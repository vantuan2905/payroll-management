package org.example.payroll_management.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.payroll_management.dao.ChiTietChamCongRepository;
import org.example.payroll_management.dao.HolidayRepository;
import org.example.payroll_management.dao.OverTimeRepository;
import org.example.payroll_management.model.ChamCong;
import org.example.payroll_management.model.ChiTietChamCong;
import org.example.payroll_management.model.NghiLe;
import org.example.payroll_management.model.OverTime;
import org.example.payroll_management.service.ChamCongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@RequestMapping("/chamCong")
public class ChamCongController {
    private final GlobalVariables globalVariables;
    @Autowired
    ChamCongService chamCongService;
    @Autowired
    ChiTietChamCongRepository chiTietChamCongRepository;
    @Autowired
    OverTimeRepository overTimeRepository;
    @Autowired
    HolidayRepository holidayRepository;

    public ChamCongController(GlobalVariables globalVariables) {
        this.globalVariables = globalVariables;
    }

    @GetMapping("/user")
    public String chamCong(@CookieValue(value = "ma") String user,Model model) {
        LocalDate currentDate = LocalDate.now();
        int thang = currentDate.getMonthValue();
        int nam = currentDate.getYear();
        List<ChamCong> chamCongThang=chamCongService.layChamCongTheoThangNam(user,thang,nam);
        System.out.println(chamCongThang.size());
        model.addAttribute("danhSachChamCong",chamCongThang);
        model.addAttribute("active", "chamCong");
        model.addAttribute("thang",thang);
        model.addAttribute("nam",nam);
        model.addAttribute("manv",user);
        model.addAttribute("checkIn",globalVariables.getCheckIn());
        model.addAttribute("checkOut",globalVariables.getCheckOut());
        thongKe(model,chamCongThang,thang,nam);
        return "chamCong_user";

    }

    @GetMapping("/loc")
    public String chamCong(@CookieValue(value = "ma") String user,@RequestParam int thang, @RequestParam int nam, Model model) {
        System.out.println(user+thang+nam);
        List<ChamCong> chamCongThang=chamCongService.layChamCongTheoThangNam(user,thang,nam);
        System.out.println(chamCongThang.size());
        model.addAttribute("danhSachChamCong",chamCongThang);
        model.addAttribute("active", "chamCong");
        model.addAttribute("thang",thang);
        model.addAttribute("nam",nam);
        model.addAttribute("manv",user);
        model.addAttribute("checkIn",globalVariables.getCheckIn());
        model.addAttribute("checkOut",globalVariables.getCheckOut());
        thongKe(model,chamCongThang,thang,nam);
        return "chamCong_user";
    }


    public void thongKe(Model model,List<ChamCong> chamCongThang,int thang,int nam){
        String month="";
        if(thang<10) month="0"+thang;
        else month=thang+"";
        List<OverTime> overtimes=overTimeRepository.findAll();
        List<NghiLe> nghiLes=holidayRepository.findHolidaysByMonth(month);
        System.out.println(nghiLes.size());
        List<NghiLe> nghiLeAm=holidayRepository.timNghiLeAm();

        Set<String>  nghiLeSet= new HashSet<>();
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
                }
            }
            // Define the desired format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM");
            String formattedDate = chamCong.getWorkDate().format(formatter);
            System.out.println(formattedDate);
            if(nghiLeSet.contains(formattedDate)){
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
