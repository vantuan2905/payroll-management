package org.example.payroll_management.controller;

import org.example.payroll_management.service.PhieuLuongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("phieuLuong")
public class PhieuLuongController {
    @Autowired
    PhieuLuongService phieuLuongService;

    @GetMapping("/")
    public String phieuLuong(Model model) {
        model.addAttribute("active","phieuLuong");
        return "phieuLuong_user";
    }
}
