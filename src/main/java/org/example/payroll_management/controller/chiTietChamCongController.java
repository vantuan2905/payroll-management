package org.example.payroll_management.controller;

import org.example.payroll_management.dao.ChiTietChamCongRepository;
import org.example.payroll_management.model.ChiTietChamCong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/chiTiet")
public class chiTietChamCongController {
    @Autowired
    ChiTietChamCongRepository chiTietChamCongRepository;

    @GetMapping("/")
    public List<ChiTietChamCong> getChiTiet(@RequestParam LocalDate ngayCong){
        return chiTietChamCongRepository.findByDate(ngayCong);
    }
}
