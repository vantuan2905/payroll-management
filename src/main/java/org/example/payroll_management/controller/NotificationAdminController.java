package org.example.payroll_management.controller;

import org.example.payroll_management.dao.NotificationRepository;
import org.example.payroll_management.model.Notifications;
import org.example.payroll_management.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/notification")
public class NotificationAdminController {
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    NhanVienService nhanVienService;

    @GetMapping("/admin")
    public String getNotifications(Model model) {
        List<Notifications> notifications = notificationRepository.findAll();
        model.addAttribute("notifications", notifications);
        model.addAttribute("active", "hoTro");
        return "notification_admin";
    }
    @GetMapping("/giaiQuyet")
    public String getNotifications(Model model,@RequestParam Integer notificationId) {
        Notifications notification=notificationRepository.findById(notificationId).get();
        notification.setTrangThai("Đã giải quyết");
        notificationRepository.save(notification);
        model.addAttribute("active", "hoTro");
        return "redirect:/notification/admin";
    }
}
