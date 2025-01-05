package org.example.payroll_management.controller;

import org.example.payroll_management.dao.NotificationRepository;
import org.example.payroll_management.model.Notifications;
import org.example.payroll_management.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@RestController
@RequestMapping("/notifications")
@CrossOrigin(origins="*")
public class NotificationController {
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    NhanVienService nhanVienService;

    @GetMapping("/")
    public String saveNotification(@RequestParam String manv, @RequestParam String ghiChu) {
        Notifications notification = new Notifications();
        notification.setNgayTao(LocalDate.now());
        notification.setTenNV(nhanVienService.layNhanVienTheoId(manv).getHoTen());
        notification.setNoiDung(ghiChu);
        notification.setTrangThai("Chưa giải quyết");
        notificationRepository.save(notification);
        return ghiChu;
    }

}
