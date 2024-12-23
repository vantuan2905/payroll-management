package org.example.payroll_management.service;

import jakarta.transaction.Transactional;
import org.example.payroll_management.dao.ChucVuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ChucVuService {
    @Autowired
    ChucVuRepository chucVuRepository;

    public String getTenCV(String maCV){
        return chucVuRepository.findById(maCV).get().getTenCV();
    }
}
