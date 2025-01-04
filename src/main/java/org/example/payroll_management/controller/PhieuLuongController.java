package org.example.payroll_management.controller;

import org.example.payroll_management.dao.KhauTruRepository;
import org.example.payroll_management.dao.LuongCoBanRepository;
import org.example.payroll_management.dao.OverTimeRepository;
import org.example.payroll_management.dao.PhuCapRepository;
import org.example.payroll_management.model.*;
import org.example.payroll_management.service.ChamCongService;
import org.example.payroll_management.service.NhanVienService;
import org.example.payroll_management.service.PhieuLuongService;
import org.example.payroll_management.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("phieuLuong")
public class PhieuLuongController {
    private final GlobalVariables globalVariables;
    @Autowired
    PhieuLuongService phieuLuongService;
    @Autowired
    ChamCongService chamCongService;
    @Autowired
    ThongKeService thongKeService;
    @Autowired
    NhanVienService nhanVienService;
    @Autowired
    LuongCoBanRepository luongCoBanRepository;
    @Autowired
    PhuCapRepository phuCapRepository;
    @Autowired
    KhauTruRepository khauTruRepository;
    @Autowired
    private OverTimeRepository overTimeRepository;

    public PhieuLuongController(GlobalVariables globalVariables) {
        this.globalVariables = globalVariables;
    }

    @GetMapping("/")
    public String phieuLuong(@CookieValue(value = "ma") String user, Model model) {
        LocalDate currentDate = LocalDate.now();
        int thang = currentDate.getMonthValue();
        int nam = currentDate.getYear();
//        List<ChamCong> chamCongThang=chamCongService.layChamCongTheoThangNam(user,thang,nam);
//        System.out.println(chamCongThang.size());
//        thongKeService.thongKe(model,chamCongThang,thang,nam);
        NhanVien nhanVien=nhanVienService.layNhanVienTheoId(user);
//        System.out.println(nhanVien.maPB+" "+nhanVien.maCV+" "+globalVariables.getPhatDiMuonVeSom()+" "+globalVariables.getPhatNghiKhongPhep());
        PhieuLuong phieuLuong = phieuLuongService.layPhieuLuongUser(thang,nam,user);
        if (phieuLuong == null) {
            phieuLuong =phieuLuongService.layPhieuLuongTheoId(12+0L);
            model.addAttribute("isExist","0");
        }else{
            model.addAttribute("isExist","1");
        }
        NhanVien nv=nhanVienService.layNhanVienTheoId(user);
        List<ChamCong> chamCong = chamCongService.layChamCongTheoThangNam(user, thang, nam);
        List<PhuCap> danhSachPhuCap = phuCapRepository.findAllByMaPBAndMaCV(nv.maPB, nv.maCV);
        List<KhauTru> danhSachKhauTru = khauTruRepository.findAllByMaPBAndMaCV(nv.maPB, nv.maCV);
        ThongKe thongKe= thongKeService.thongKe(model,chamCong,thang,nam);
        double ngayThucTe = Math.round((thongKe.thoiGianLamViec / 8) * 10.0) / 10.0;
        thongKe.ngayThucTe = ngayThucTe;
        phieuLuong.setThongKe(thongKe);
        phieuLuong.setDanhSachPhuCap(danhSachPhuCap);
        phieuLuong.setDanhSachKhauTru(danhSachKhauTru);
        phieuLuong.setLuongCoBan(luongCoBanRepository.findByMaPBAndMaCV(nv.maPB,nv.maCV).mucLuong);
        System.out.println(phieuLuong+"...");

        System.out.println(phieuLuong.getId());
        model.addAttribute("thang",thang);
        model.addAttribute("nam",nam);
        model.addAttribute("phieuLuong",phieuLuong);
        model.addAttribute("ten",nhanVien.hoTen);
        model.addAttribute("phieuLuong",phieuLuong);
        model.addAttribute("active","phieuLuong");
        return "phieuLuong_user";
    }
    @GetMapping("/loc")
    public String locPhieuLuong(@CookieValue(value = "ma") String user, @RequestParam int thang,@RequestParam int nam, Model model) {
        System.out.println(thang+" "+nam+" --------------------          "+user);
        NhanVien nhanVien=nhanVienService.layNhanVienTheoId(user);
        PhieuLuong phieuLuong = phieuLuongService.layPhieuLuongUser(thang,nam,user);
        if (phieuLuong == null) {
            phieuLuong =phieuLuongService.layPhieuLuongTheoId(12+0L);
            model.addAttribute("isExist","0");

        }else{
            model.addAttribute("isExist","1");
        }
        NhanVien nv=nhanVienService.layNhanVienTheoId(user);
        List<ChamCong> chamCong = chamCongService.layChamCongTheoThangNam(user, thang, nam);
        List<PhuCap> danhSachPhuCap = phuCapRepository.findAllByMaPBAndMaCV(nv.maPB, nv.maCV);
        System.out.println("------------++++++++++++++++++++++++++++++++++++++++"+danhSachPhuCap.size());
        List<KhauTru> danhSachKhauTru = khauTruRepository.findAllByMaPBAndMaCV(nv.maPB, nv.maCV);
        ThongKe thongKe= thongKeService.thongKe(model,chamCong,thang,nam);
        double ngayThucTe = Math.round((thongKe.thoiGianLamViec / 8) * 10.0) / 10.0;
        thongKe.ngayThucTe = ngayThucTe;
        phieuLuong.setThongKe(thongKe);
        phieuLuong.setDanhSachPhuCap(danhSachPhuCap);
        phieuLuong.setDanhSachKhauTru(danhSachKhauTru);
        phieuLuong.setLuongCoBan(luongCoBanRepository.findByMaPBAndMaCV(nv.maPB,nv.maCV).mucLuong);
        System.out.println(phieuLuong+"...");

        model.addAttribute("thang",thang);
        model.addAttribute("nam",nam);
        model.addAttribute("ten",nhanVien.hoTen);
        model.addAttribute("phieuLuong",phieuLuong);
        model.addAttribute("active","phieuLuong");
        return "phieuLuong_user";
    }
    @GetMapping("/admin/loc")
    public String locPhieuLuongAdmin(@CookieValue(value = "ma") String user, @RequestParam int thang,@RequestParam int nam, Model model) {
        List<PhieuLuong> danhSachPhieuLuong = phieuLuongService.layPhieuLuongTheoThangVaNam(thang,nam);
        if(danhSachPhieuLuong.size()==0) {
            model.addAttribute("isExist", "0");
        }else {
            model.addAttribute("isExist", "1");
        }
        if(danhSachPhieuLuong.size() == 0){
            PhieuLuong phieuLuong = phieuLuongService.layPhieuLuongTheoId(12+0L);
            danhSachPhieuLuong.add(phieuLuong);
        }
        for(PhieuLuong phieuLuong:danhSachPhieuLuong){
            NhanVien nv=nhanVienService.layNhanVienTheoId(phieuLuong.getManv());
            List<ChamCong> chamCong = chamCongService.layChamCongTheoThangNam(phieuLuong.getManv(), thang, nam);
            List<PhuCap> danhSachPhuCap = phuCapRepository.findAllByMaPBAndMaCV(nv.maPB, nv.maCV);
            System.out.println("------------++++++++++++++++++++++++++++++++++++++++"+danhSachPhuCap.size());
            List<KhauTru> danhSachKhauTru = khauTruRepository.findAllByMaPBAndMaCV(nv.maPB, nv.maCV);
            ThongKe thongKe= thongKeService.thongKe(model,chamCong,thang,nam);
            double ngayThucTe = Math.round((thongKe.thoiGianLamViec / 8) * 10.0) / 10.0;
            thongKe.ngayThucTe = ngayThucTe;
            phieuLuong.setThongKe(thongKe);
            phieuLuong.setDanhSachPhuCap(danhSachPhuCap);
            phieuLuong.setDanhSachKhauTru(danhSachKhauTru);
            phieuLuong.setLuongCoBan(luongCoBanRepository.findByMaPBAndMaCV(nv.maPB,nv.maCV).mucLuong);
        }


        model.addAttribute("thang",thang);
        model.addAttribute("nam",nam);
        model.addAttribute("danhSachPhieuLuong",danhSachPhieuLuong);
        model.addAttribute("active","phieuLuong");
        System.out.println(danhSachPhieuLuong.size()+"  ++++******-----");
        return "phieuluong_admin";
    }

    @GetMapping("/admin")
    public String phieuLuongAdmin(@CookieValue(value = "ma") String user, Model model) {
        LocalDate currentDate = LocalDate.now();
        int thang = currentDate.getMonthValue();
        int nam = currentDate.getYear();
        List<PhieuLuong> danhSachPhieuLuong = phieuLuongService.layPhieuLuongTheoThangVaNam(thang,nam);
        if(danhSachPhieuLuong.size()==0) {
            model.addAttribute("isExist", "0");
        }else {
            model.addAttribute("isExist", "1");
        }
        if(danhSachPhieuLuong.size() == 0){
            PhieuLuong phieuLuong = phieuLuongService.layPhieuLuongTheoId(12+0L);
            danhSachPhieuLuong.add(phieuLuong);
        }
        for(PhieuLuong phieuLuong:danhSachPhieuLuong){
            NhanVien nv=nhanVienService.layNhanVienTheoId(phieuLuong.getManv());
            List<ChamCong> chamCong = chamCongService.layChamCongTheoThangNam(phieuLuong.getManv(), thang, nam);
            List<PhuCap> danhSachPhuCap = phuCapRepository.findAllByMaPBAndMaCV(nv.maPB, nv.maCV);
            System.out.println("------------++++++++++++++++++++++++++++++++++++++++"+danhSachPhuCap.size());
            List<KhauTru> danhSachKhauTru = khauTruRepository.findAllByMaPBAndMaCV(nv.maPB, nv.maCV);
            ThongKe thongKe= thongKeService.thongKe(model,chamCong,thang,nam);
            double ngayThucTe = Math.round((thongKe.thoiGianLamViec / 8) * 10.0) / 10.0;
            thongKe.ngayThucTe = ngayThucTe;
            phieuLuong.setThongKe(thongKe);
            phieuLuong.setDanhSachPhuCap(danhSachPhuCap);
            phieuLuong.setDanhSachKhauTru(danhSachKhauTru);
            phieuLuong.setLuongCoBan(luongCoBanRepository.findByMaPBAndMaCV(nv.maPB,nv.maCV).mucLuong);
        }


        model.addAttribute("thang",thang);
        model.addAttribute("nam",nam);
        model.addAttribute("danhSachPhieuLuong",danhSachPhieuLuong);
        model.addAttribute("active","phieuLuong");
        System.out.println(danhSachPhieuLuong.size()+"  ++++******-----");
        return "phieuluong_admin";
    }
    @GetMapping("/tinhLuong")
    public String tinhLuong(Model model,@RequestParam Integer thang,@RequestParam Integer nam) {
        List<NhanVien> danhSachNhanVien = nhanVienService.layDanhSachNhanVien();
        System.out.println(danhSachNhanVien.size()+" "+thang+" "+nam);
        List<PhieuLuong> danhSachPhieuLuong = phieuLuongService.layPhieuLuongTheoThangVaNam(thang,nam);
        if(danhSachPhieuLuong.size()>0){
            for(PhieuLuong phieuLuong:danhSachPhieuLuong){
                System.out.println(phieuLuong.getDanhSachKhauTru());
            }
        }else {
            int phatDiMuonVeSom = globalVariables.getPhatDiMuonVeSom();
            int phatNghi = globalVariables.getPhatNghiKhongPhep();
            for (NhanVien nv : danhSachNhanVien) {
                System.out.println(nv.getMaNV() + " " + thang + " " + nam);
                List<ChamCong> chamCong = chamCongService.layChamCongTheoThangNam(nv.getMaNV(), thang, nam);
                ThongKe thongKe = thongKeService.thongKe(model, chamCong, thang, nam);
                long luongCoBan = luongCoBanRepository.findByMaPBAndMaCV(nv.maPB, nv.maCV).getMucLuong();
                List<PhuCap> danhSachPhuCap = phuCapRepository.findAllByMaPBAndMaCV(nv.maPB, nv.maCV);
                List<KhauTru> danhSachKhauTru = khauTruRepository.findAllByMaPBAndMaCV(nv.maPB, nv.maCV);
                System.out.println(danhSachPhuCap.size() + " " + danhSachKhauTru.size());
                PhieuLuong phieuLuong = new PhieuLuong();
                phieuLuong.setLuongCoBan(luongCoBan);
                long tongPhuCap = 0;
                for (PhuCap phuCap : danhSachPhuCap) {
                    tongPhuCap += phuCap.mucPhuCap;
                }

                double ngayThucTe = Math.round((thongKe.thoiGianLamViec / 8) * 10.0) / 10.0;
                thongKe.ngayThucTe = ngayThucTe;
                long tongLuong = (long) Math.ceil((luongCoBan + tongPhuCap) * ((ngayThucTe + thongKe.nghiLe + thongKe.nghiPhep) / thongKe.ngayCongChuan));
                List<OverTime> overtimes=overTimeRepository.findAll();
                Map<String, Double> overTimeMap = new HashMap<>();
                for (OverTime overTime : overtimes) {
                    overTimeMap.put(overTime.loai,overTime.getHeSo());
                    System.out.println(overTime.getLoai()+" "+overTime.getHeSo() );
                }
                long luongTheoGio =(long) (luongCoBan + tongPhuCap) / (thongKe.ngayCongChuan*8);
                System.out.println(luongTheoGio+" *------");
                tongLuong+=luongTheoGio*(thongKe.otNgayThuong*overTimeMap.get("thuong")+thongKe.otNgayNghi*overTimeMap.get("nghi")+ thongKe.nghiLe*overTimeMap.get("le"));
                long tongTru = 0;
                for (KhauTru khauTru : danhSachKhauTru) {
                    tongTru += (int) Math.ceil(khauTru.getMucKhauTru() * tongLuong);
                }
                tongTru += thongKe.nghiKhongPhep * phatNghi + thongKe.diMuonVeSom * phatDiMuonVeSom;
                phieuLuong.setTongLuong(tongLuong);
                phieuLuong.setTongTru(tongTru);
                phieuLuong.setLuongCoBan(luongCoBan);
                phieuLuong.setDanhSachPhuCap(danhSachPhuCap);
                phieuLuong.setDanhSachKhauTru(danhSachKhauTru);
                phieuLuong.setThucLinh(tongLuong - tongTru);
                phieuLuong.setThang(thang);
                phieuLuong.setNam(nam);
                phieuLuong.setThongKe(thongKe);
                phieuLuong.setManv(nv.getMaNV());
                phieuLuongService.luuPhieuLuong(phieuLuong);
                danhSachPhieuLuong.add(phieuLuong);
                System.out.println(ngayThucTe + " ---  " + tongLuong);
                System.out.println(phieuLuong);
            }
        }
        model.addAttribute("isExist","0");
        model.addAttribute("thang",thang);
        model.addAttribute("nam",nam);
        model.addAttribute("phieuLuong",danhSachPhieuLuong);
        model.addAttribute("active","phieuLuong");
        return "redirect:/phieuLuong/admin/loc?thang="+thang+"&nam="+nam;
    }
}
