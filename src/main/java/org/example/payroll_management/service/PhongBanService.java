package org.example.payroll_management.service;

import jakarta.transaction.Transactional;
import org.example.payroll_management.dao.PhongBanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PhongBanService {
    @Autowired
    PhongBanRepository phongBanRepository;

    public String tenPhongBan(String maPB){
        return phongBanRepository.findById(maPB).get().getTenPB();
    }
    public String tenPhongBan2(String maPB){
        return maPB;
    }
}
