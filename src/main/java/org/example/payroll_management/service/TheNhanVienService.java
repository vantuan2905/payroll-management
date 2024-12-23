package org.example.payroll_management.service;

import org.example.payroll_management.dao.TheNhanVienRepository;
import org.example.payroll_management.model.TheNhanVien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TheNhanVienService {
    @Autowired
    private TheNhanVienRepository theNhanVienRepository;

    public String MaNhanVien(String maThe){
        TheNhanVien the=theNhanVienRepository.findById(maThe).get();

        return the.getMaNV();
    }
}
