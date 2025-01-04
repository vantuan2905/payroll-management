package org.example.payroll_management.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.example.payroll_management.dao.ChucVuRepository;
import org.example.payroll_management.dao.NhanVienRepository;
import org.example.payroll_management.model.Admin;
import org.example.payroll_management.model.NhanVien;
import org.example.payroll_management.service.AdminService;
import org.example.payroll_management.service.ChucVuService;
import org.example.payroll_management.service.NhanVienService;
import org.example.payroll_management.service.PhongBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private NhanVienService nhanVienService;
    @Autowired
    private ChucVuService chucVuService;
    @Autowired
    private PhongBanService phongBanService;

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletResponse response, Model model) {
        System.out.println(username+" "+password);
        //check admin
        List<Admin> danhSachAdmin=adminService.layDanhSachAdmin();
        for(Admin admin:danhSachAdmin){
            if(admin.getUsername().equals(username) && admin.getPassword().equals(password)){
                Cookie role = new Cookie("role", "admin");
                Cookie user = new Cookie("ma", admin.getMaAdmin());
                response.addCookie(role);
                response.addCookie(user);
                user.setPath("/");
                user.setMaxAge(7 * 24 * 60 * 60);
                response.addCookie(role);
                response.addCookie(user);
                model.addAttribute("active","trangChu");
                return "index_admin";
            }
        }
        //check Nhan vien
        List<NhanVien> danhSachNhanVien=nhanVienService.layDanhSachNhanVien();
        for(NhanVien nhanVien:danhSachNhanVien){
            if(nhanVien.getUsername().equals(username) && nhanVien.getPassword().equals(password)){
                Cookie role = new Cookie("role", "user");
                Cookie user = new Cookie("ma", nhanVien.getMaNV());
                // Set the path (optional)
                user.setPath("/");
                user.setMaxAge(7 * 24 * 60 * 60);
                response.addCookie(role);
                response.addCookie(user);
                System.out.println(nhanVien.maCV);
                //nhanVien.maPB=phongBanService.tenPhongBan(nhanVien.maPB);
                nhanVien.maCV=chucVuService.getTenCV(nhanVien.maCV);
                String ten = nhanVien.getHoTen().trim().isEmpty() ? "" : nhanVien.getHoTen().substring(nhanVien.getHoTen().lastIndexOf(' ') + 1);
                model.addAttribute("user",nhanVien);
                model.addAttribute("ten",ten);
                model.addAttribute("active","trangChu");

                return "index_user";
            }
        }

        return "login";
    }
}