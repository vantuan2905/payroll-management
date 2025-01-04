package org.example.payroll_management.service;

import org.example.payroll_management.dao.PhieuLuongRepository;
import org.example.payroll_management.model.NhanVien;
import org.example.payroll_management.model.PhieuLuong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhieuLuongService {
    @Autowired
    PhieuLuongRepository phieuLuongRepository;

    public PhieuLuong layPhieuLuongUser(int thang, int nam, String manv){
            return phieuLuongRepository.getPhieuLuongByThangAndNamAndManv(thang,nam,manv);
    }
    public PhieuLuong layPhieuLuongTheoId(Long id){
        return phieuLuongRepository.findById(id).get();
    }
    public PhieuLuong luuPhieuLuong(PhieuLuong phieuLuong){
        return phieuLuongRepository.save(phieuLuong);
    }
    public List<PhieuLuong> layPhieuLuongTheoThangVaNam(int thang, int nam){
        return phieuLuongRepository.findAllByThangAndNam(thang,nam);
    }
}
