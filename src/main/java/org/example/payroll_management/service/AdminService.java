package org.example.payroll_management.service;

import jakarta.transaction.Transactional;
import org.example.payroll_management.dao.AdminRepository;
import org.example.payroll_management.model.Admin;
import org.example.payroll_management.model.NhanVien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> layDanhSachAdmin(){
        return adminRepository.findAll();
    }
}