package org.example.payroll_management.controller;

import org.example.payroll_management.dao.RequestRepository;
import org.example.payroll_management.model.Request;
import org.example.payroll_management.service.ChamCongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequestMapping("/requests")
public class RequestUserController {
    @Autowired
    RequestRepository requestRepository;

    @GetMapping("/")
    public String getAll(Model model){
        List<Request> requests = requestRepository.findAll();

        model.addAttribute("requests", requests);
        model.addAttribute("active", "yeuCau");
        return "request_user";
    }
}
