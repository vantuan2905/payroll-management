package org.example.payroll_management.controller;

import org.example.payroll_management.dao.RequestRepository;
import org.example.payroll_management.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/request")
public class RequestController {
    @Autowired
    RequestRepository requestRepository;
    @GetMapping("/chamCong")
    public Request saveChamCong(@RequestParam String manv,@RequestParam String ghiChu){
        Request request=new Request();
        request.setLoai("Chấm công");
        request.setMaNV(manv);
        request.setGhiChu(ghiChu);
        return requestRepository.save(request);
    }
}
