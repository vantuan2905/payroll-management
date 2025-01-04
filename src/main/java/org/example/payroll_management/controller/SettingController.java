package org.example.payroll_management.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.payroll_management.dao.HolidayRepository;
import org.example.payroll_management.model.NghiLe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/settings")
public class SettingController {
    @Autowired
    private GlobalVariables globalVariables;
    @Autowired
    HolidayRepository holidayRepository;


    @GetMapping("/")
    public String getSetting(Model model){
        model.addAttribute("checkIn",globalVariables.getCheckIn() );
        model.addAttribute("checkOut",globalVariables.getCheckOut() );
        model.addAttribute("maxOvertime",globalVariables.getMaxOvertime() );
        model.addAttribute("maxNghiPhep",globalVariables.getMaxNghiPhep() );
        model.addAttribute("phatNghi", globalVariables.getPhatNghiKhongPhep());
        model.addAttribute("phatDiMuonVeSom", globalVariables.getPhatDiMuonVeSom());
        List<NghiLe> tetAm = holidayRepository.timNghiLeTheoLoai("Tết nguyên đán");
        String batDau=tetAm.get(0).get_date().split("/")[0];
        String ketThuc=tetAm.get(tetAm.size()-1).get_date().split("/")[0];
        LocalDate hienTai = LocalDate.now();
        int namHienTai=hienTai.getYear();

        model.addAttribute("tetStart", chuyenNgayDuongSangAm(Integer.parseInt(batDau),Integer.parseInt(tetAm.get(0).get_date().split("/")[1]),namHienTai+1).getDayOfMonth());
        model.addAttribute("tetEnd", chuyenNgayDuongSangAm(Integer.parseInt(ketThuc),Integer.parseInt(tetAm.get(tetAm.size()-1).get_date().split("/")[1]),namHienTai+1).getDayOfMonth());
        model.addAttribute("active", "setting");
        return "setting";
    }
    @PostMapping("/")
    public String setSettings(
            @RequestParam(name = "checkIn") String checkIn,
            @RequestParam(name = "checkOut") String checkOut,
            @RequestParam(name = "maxOvertime") String maxOvertime,
            @RequestParam(name = "maxNghiPhep") String maxNghiPhep,
            @RequestParam(name = "phatNghi") String phatNghi,
            @RequestParam(name = "phatDiMuonVeSom") String phatDiMuonVeSom,
            @RequestParam(name = "tetStart") String tetStart,
            @RequestParam(name = "tetEnd") String tetEnd,
            Model model) {
        System.out.println(tetStart+" "+tetEnd);
        globalVariables.setCheckIn(checkIn);System.setProperty(checkIn,checkIn);
        globalVariables.setCheckOut(checkOut);
        globalVariables.setMaxOvertime(Integer.parseInt(maxOvertime));
        globalVariables.setMaxNghiPhep(Integer.parseInt(maxNghiPhep));
        globalVariables.setPhatNghiKhongPhep(Integer.parseInt(phatNghi));
        globalVariables.setPhatDiMuonVeSom(Integer.parseInt(phatDiMuonVeSom));

        LocalDate hienTai = LocalDate.now();
        int namHienTai=hienTai.getYear();
        LocalDate batDauNghiTet=chuyenNgayAmSangDuong(Integer.parseInt(tetStart),12,namHienTai);
        LocalDate ketThucNghiTet=chuyenNgayAmSangDuong(Integer.parseInt(tetEnd),1,namHienTai+1);
        System.out.println(batDauNghiTet);
        System.out.println(ketThucNghiTet);
        List<LocalDate> dates = new ArrayList<>();
        LocalDate currentDate = batDauNghiTet;
        while (!currentDate.isAfter(ketThucNghiTet)) {
            dates.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }

        List<NghiLe> tetNguyenDan = new ArrayList<>();
        for(LocalDate i:dates){
            NghiLe nghiLe = new NghiLe();
            nghiLe.set_date(String.format("%02d/%02d", i.getDayOfMonth(), i.getMonthValue()));
            nghiLe.setMoTa("Tết nguyên đán");
            nghiLe.setLoai(false);
            tetNguyenDan.add(nghiLe);
        }
        holidayRepository.xoaNghiLe("Tết nguyên đán");
        holidayRepository.saveAll(tetNguyenDan);
        return "redirect:/settings/";
    }

    public LocalDate chuyenNgayAmSangDuong(int ngay, int thang, int nam){
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
    public LocalDate chuyenNgayDuongSangAm(int ngay, int thang, int nam){
        RestTemplate restTemplate = new RestTemplate();

        // Define the URL for the API
        String url = "https://open.oapi.vn/date/convert-to-lunar"; // Replace with your actual API URL

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
