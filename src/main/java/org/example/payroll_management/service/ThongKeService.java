package org.example.payroll_management.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.payroll_management.controller.GlobalVariables;
import org.example.payroll_management.dao.ChiTietChamCongRepository;
import org.example.payroll_management.dao.HolidayRepository;
import org.example.payroll_management.dao.OverTimeRepository;
import org.example.payroll_management.model.ChamCong;
import org.example.payroll_management.model.NghiLe;
import org.example.payroll_management.model.OverTime;
import org.example.payroll_management.model.ThongKe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

// ChamCongService.java
@Service
public class ThongKeService {

    @Autowired
    private ChiTietChamCongRepository chiTietChamCongRepository;
    @Autowired
    private OverTimeRepository overTimeRepository;
    @Autowired
    private HolidayRepository holidayRepository;
    @Autowired
    private GlobalVariables globalVariables;


    public ThongKe thongKe(Model model, List<ChamCong> chamCongThang, int thang, int nam){
        String month="";
        if(thang<10) month="0"+thang;
        else month=thang+"";
        List<OverTime> overtimes=overTimeRepository.findAll();
        List<NghiLe> nghiLes=holidayRepository.findHolidaysByMonth(month);
        System.out.println(nghiLes.size());
        List<NghiLe> nghiLeAm=holidayRepository.timNghiLeAm();

        Set<String> nghiLeSet= new HashSet<>();
        for(NghiLe nghiLe:nghiLes){
            nghiLeSet.add(nghiLe.get_date());
        }

        System.out.println(nghiLeAm.size()+"..");
        for(NghiLe nghiLe:nghiLeAm){
            String[] ds=nghiLe.get_date().split("/");
            LocalDate checkDate=chuyenNgayAmSangDuong(Integer.parseInt(ds[0]),Integer.parseInt(ds[1]),nam);
            System.out.println(checkDate);
            if(checkDate.getMonthValue()==thang) nghiLes.add(nghiLe);
        }

        double thoiGianLamViec=0;
        double otNgayThuong=0;
        int ot=0;
        double otNgayNghi=0;
        double otNgayLe=0;
        int diSomVeMuon=0;
        int nghiPhep=0;
        int nghiKhongLuong=0;
        int nghiKhongPhep=0;
        int nghiLe=0;
        int ngayCongChuan=0;

        for(ChamCong chamCong:chamCongThang){
            // Skip records where the date is Saturday or Sunday and isOverTime is false
            if ((chamCong.getWorkDate().getDayOfWeek() == DayOfWeek.SATURDAY ||
                    chamCong.getWorkDate().getDayOfWeek() == DayOfWeek.SUNDAY)) {
                if(chamCong.getIsOverTime()==false)
                    continue;
                else{
                    ot++;
                    otNgayNghi+=chamCong.getOverTimeHour();
                    continue;
                }
            }
            // Define the desired format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
            String formattedDate = chamCong.getWorkDate().format(formatter);
            System.out.println(formattedDate);
            if(nghiLeSet.contains(formattedDate)){
                nghiLe++;
                if(chamCong.getIsOverTime()==false){
                    continue;
                }else{
                    ot++;
                    otNgayLe+=chamCong.getOverTimeHour();
                }
            }
            ngayCongChuan++;
            // Print the formatted date
            System.out.println("Formatted Date: " + formattedDate);
            thoiGianLamViec+=chamCong.getWorkingHours();
            if(chamCong.getWorkingHours()==0){
                if(chamCong.getIsPaidLeaveDay()) nghiPhep++;
                else{
                    if(chamCong.getIsApprovedLeaveDay()) nghiKhongLuong++;
                    else nghiKhongPhep++;
                }
                continue;
            }
            if(chamCong.getIsLate()!=null && chamCong.getIsLate()) diSomVeMuon++;
            if(chamCong.getIsEarly()!=null && chamCong.getIsEarly()) diSomVeMuon++;
            if(chamCong.getIsOverTime()){
                ot++;
                otNgayThuong+=chamCong.getOverTimeHour();
            }

        }
        model.addAttribute("thoiGianLamViec", thoiGianLamViec);
        model.addAttribute("otNgayThuong", otNgayThuong);
        model.addAttribute("ot", ot);
        model.addAttribute("otNgayNghi", otNgayNghi);
        model.addAttribute("otNgayLe", otNgayLe);
        model.addAttribute("diSomVeMuon", diSomVeMuon);
        model.addAttribute("nghiPhep", nghiPhep);
        model.addAttribute("nghiKhongLuong", nghiKhongLuong);
        model.addAttribute("nghiKhongPhep", nghiKhongPhep);
        model.addAttribute("nghiLe", nghiLe);
        model.addAttribute("ngayCongChuan", ngayCongChuan);
        // Print all variables using System.out.println
        System.out.println("Thời gian làm việc: " + thoiGianLamViec);
        System.out.println("OT Ngày Thường: " + otNgayThuong);
        System.out.println("OT Tổng: " + ot);
        System.out.println("OT Ngày Nghỉ: " + otNgayNghi);
        System.out.println("OT Ngày Lễ: " + otNgayLe);
        System.out.println("Đi Sớm Về Muộn: " + diSomVeMuon);
        System.out.println("Nghỉ Phép: " + nghiPhep);
        System.out.println("Nghỉ Không Lương: " + nghiKhongLuong);
        System.out.println("Nghỉ Không Phép: " + nghiKhongPhep);
        System.out.println("Nghỉ Lễ: " + nghiLe);
        System.out.println("Ngày Công Chuẩn: " + ngayCongChuan);
        return new ThongKe(thoiGianLamViec, otNgayThuong, ot, otNgayNghi, otNgayLe, diSomVeMuon, nghiPhep,
                nghiKhongLuong, nghiKhongPhep, nghiLe, ngayCongChuan);
    }

    public LocalDate chuyenNgayAmSangDuong(int ngay,int thang,int nam){
        RestTemplate restTemplate = new RestTemplate();

        // Define the URL for the API
        String url = "https://open.oapi.vn/date/convert-to-solar"; // Replace with your actual API URL

        // Create the request body as a Map
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("day", ngay);
        requestBody.put("month", thang);
        requestBody.put("year", nam);

        // Set the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Wrap the request body and headers in an HttpEntity
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Make the POST request
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        try {
            System.out.println(response.toString());
            // Parse JSON using Jackson ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            // Navigate to the "data" node and extract day, month, year
            JsonNode dataNode = rootNode.get("data");
            int day = dataNode.get("day").asInt();
            int month = dataNode.get("month").asInt();
            int year = dataNode.get("year").asInt();
            // Create and return LocalDate
            return LocalDate.of(year, month, day);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON or extracting date fields", e);
        }
    }
}