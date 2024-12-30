package org.example.payroll_management.controller;

import org.example.payroll_management.dao.RequestRepository;
import org.example.payroll_management.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/request")
@CrossOrigin(origins="*")
public class RequestController {
    @Autowired
    RequestRepository requestRepository;
    @GetMapping("/chamCong")
    public Request saveChamCong(@RequestParam String manv, @RequestParam String ghiChu){
        Request request=new Request();
        request.setLoai("Chấm công");
        request.setMaNV(manv);
        request.setGhiChu(ghiChu);
        request.setTrangThai("Chưa xử lí");
        request.setNgayTao(LocalDate.now());
        return requestRepository.save(request);
    }
    @GetMapping("/overtime")
    public Request saveOT(@RequestParam String manv, @RequestParam String ghiChu){
        Request request=new Request();
        request.setLoai("Overtime");
        request.setMaNV(manv);
        request.setGhiChu(ghiChu);
        request.setTrangThai("Chưa xử lí");
        request.setNgayTao(LocalDate.now());
        return requestRepository.save(request);
    }
    @GetMapping("/nghi")
    public Request saveNghi(@RequestParam String manv, @RequestParam String ghiChu){
        Request request=new Request();
        request.setLoai("Nghỉ phép");
        request.setMaNV(manv);
        request.setGhiChu(ghiChu);
        request.setTrangThai("Chưa xử lí");
        request.setNgayTao(LocalDate.now());
        return requestRepository.save(request);
    }
}
