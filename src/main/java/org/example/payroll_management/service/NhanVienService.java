package org.example.payroll_management.service;

import jakarta.transaction.Transactional;
import org.example.payroll_management.dao.NhanVienRepository;
import org.example.payroll_management.model.NhanVien;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class NhanVienService {
    @Autowired
    NhanVienRepository NhanVienRepository;

    public List<NhanVien> layDanhSachNhanVien(){
        return NhanVienRepository.findAll();
    }
    public NhanVien layNhanVienTheoId(String id){
        return NhanVienRepository.findById(id).get();
    }

}
