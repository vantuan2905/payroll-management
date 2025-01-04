package org.example.payroll_management.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.payroll_management.dao.ChiTietChamCongRepository;
import org.example.payroll_management.dao.HolidayRepository;
import org.example.payroll_management.dao.OverTimeRepository;
import org.example.payroll_management.model.*;
import org.example.payroll_management.service.ChamCongService;
import org.example.payroll_management.service.ThongKeService;
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
    @Autowired
    ThongKeService thongKeService;

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
        thongKeService.thongKe(model,chamCongThang,thang,nam);
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
        thongKeService.thongKe(model,chamCongThang,thang,nam);
        return "chamCong_user";
    }

    @GetMapping("/admin")
    public String chamCong(Model model) {
        model.addAttribute("active", "chamCong");
        return "chamCong_admin";

    }
}
