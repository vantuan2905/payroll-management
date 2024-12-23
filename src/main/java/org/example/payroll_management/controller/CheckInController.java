package org.example.payroll_management.controller;

import org.example.payroll_management.dao.ChamCongRepository;
import org.example.payroll_management.dao.ChiTietChamCongRepository;
import org.example.payroll_management.dao.TheNhanVienRepository;
import org.example.payroll_management.model.ChamCong;
import org.example.payroll_management.model.ChiTietChamCong;
import org.example.payroll_management.service.ChamCongService;
import org.example.payroll_management.service.TheNhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("/checkIn")
public class CheckInController {

    private final GlobalVariables globalVariables;
    @Autowired
    ChamCongService chamCongService;
    @Autowired
    TheNhanVienService theNhanVienService;
    @Autowired
    private ChiTietChamCongRepository chiTietChamCongRepository;
    @Autowired
    public CheckInController(GlobalVariables globalVariables) {
        this.globalVariables = globalVariables;
    }

    @GetMapping("/")
    public String checkIn(@RequestParam String uid) {

        String maNV=theNhanVienService.MaNhanVien(uid);
        ChiTietChamCong checkIn=new ChiTietChamCong();
        checkIn.setEmployeeId(maNV);
        checkIn.setDate(LocalDate.now());
        checkIn.setCheckIn(LocalTime.now());
        chiTietChamCongRepository.save(checkIn);
        System.out.println(LocalDate.now());
        ChamCong chamCong=chamCongService.layChamCongTheoWorkDate(LocalDate.now(),maNV);
        chamCong.setHoliday(0);
        System.out.println(chamCong.getCheckIn());
        System.out.println(globalVariables.getCheckIn()+"dd");
        if(chamCong.getCheckIn()==null){
            chamCong.setCheckIn(checkIn.getCheckIn());
            LocalTime standardCheckInTime = LocalTime.parse(globalVariables.getCheckIn());
            System.out.println(standardCheckInTime);
            if(checkIn.getCheckIn().isAfter(standardCheckInTime)){
                chamCong.setIsLate(true);
                System.out.println(1);
            }
            chamCongService.luuChamCong(chamCong);
        }
        return maNV;
    }

}
