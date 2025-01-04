package org.example.payroll_management.controller;

import org.example.payroll_management.dao.NhanVienRepository;
import org.example.payroll_management.model.NhanVien;
import org.example.payroll_management.service.ChucVuService;
import org.example.payroll_management.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    NhanVienService nhanVienService;
    @Autowired
    private ChucVuService chucVuService;


    @GetMapping("/index")
    public String index(@CookieValue(value = "ma") String user, Model model) {
        NhanVien nhanVien= nhanVienService.layNhanVienTheoId(user);
        nhanVien.maCV=chucVuService.getTenCV(nhanVien.maCV);
        String ten = nhanVien.getHoTen().trim().isEmpty() ? "" : nhanVien.getHoTen().substring(nhanVien.getHoTen().lastIndexOf(' ') + 1);
        model.addAttribute("user",nhanVien);
        model.addAttribute("ten",ten);
        System.out.println(ten);
        model.addAttribute("active","trangChu");
        return "index_user";
    }

    @GetMapping("/admin")
    public String index_admin(@CookieValue(value = "ma") String user, Model model) {

        model.addAttribute("active","trangChu");
        return "index_admin";
    }
}
