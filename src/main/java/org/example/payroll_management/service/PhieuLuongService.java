package org.example.payroll_management.service;

import org.example.payroll_management.dao.PhieuLuongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhieuLuongService {
    @Autowired
    PhieuLuongRepository phieuLuongRepository;

}
