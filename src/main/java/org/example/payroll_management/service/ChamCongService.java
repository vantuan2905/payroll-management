package org.example.payroll_management.service;

import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import org.example.payroll_management.dao.ChamCongRepository;
import org.example.payroll_management.model.ChamCong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ChamCongService {
    @Autowired
    private ChamCongRepository chamCongRepository;

    public void luuChamCong(ChamCong chamCong) {
        chamCongRepository.save(chamCong);
    }

    public List<ChamCong> layChamCongTheoThangNam(String maNV, int thang, int nam){
        String date = String.format("%04d-%02d%%", nam, thang);
        System.out.println(date);
        return chamCongRepository.layChamCongNhanVienTheoThangVaNam(maNV,nam,thang);
    }

    public ChamCong layChamCongTheoWorkDate(LocalDate workDate,String employeeId){
        System.out.println(workDate+"   "+employeeId);
        return chamCongRepository.findByWorkDateAndEmployeeId(workDate,employeeId);
    }

    public int countPaidLeaveDay(String employeeId,int nam){
        return chamCongRepository.countPaidLeaveDay(employeeId,nam);
    }
}
