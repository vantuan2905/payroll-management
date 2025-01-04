package org.example.payroll_management.controller;

import org.example.payroll_management.dao.AdminRepository;
import org.example.payroll_management.dao.ChiTietChamCongRepository;
import org.example.payroll_management.dao.RequestRepository;
import org.example.payroll_management.model.Admin;
import org.example.payroll_management.model.ChamCong;
import org.example.payroll_management.model.ChiTietChamCong;
import org.example.payroll_management.model.Request;
import org.example.payroll_management.service.ChamCongService;
import org.example.payroll_management.service.NhanVienService;
import org.example.payroll_management.service.TheNhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
@Controller
@RequestMapping("/requests")
public class RequestUserController {
    @Autowired
    RequestRepository requestRepository;
    @Autowired
    NhanVienService nhanVienService;
    @Autowired
    AdminRepository adminRepository;
    private final GlobalVariables globalVariables;
    @Autowired
    ChamCongService chamCongService;
    @Autowired
    private ChiTietChamCongRepository chiTietChamCongRepository;
    @Autowired
    public RequestUserController(GlobalVariables globalVariables) {
        this.globalVariables = globalVariables;
    }

    @GetMapping("/")
    public String getAll(Model model){
        List<Request> requests = requestRepository.findAll();

        model.addAttribute("requests", requests);
        model.addAttribute("active", "yeuCau");
        return "request_user";
    }

    @GetMapping("/admin")
    public String getRequestUser(Model model){
        List<Request> requests=requestRepository.findAllByTrangThai("Chưa xử lí");
        for(Request request : requests){
            request.setTen(nhanVienService.layNhanVienTheoId(request.getMaNV()).getHoTen());
        }
        System.out.println(requests.size());
        model.addAttribute("requests", requests);
        model.addAttribute("active", "yeuCau");
        return "request_admin";
    }

    @PostMapping("/duyet")
    public String duyet(@CookieValue(value = "ma") String user,Model model, @RequestParam String requestId){
        System.out.println(requestId);
        Request request = requestRepository.findById(Integer.parseInt(requestId)).get();
        if(request.getLoai().equals("Chấm công")){
            ChiTietChamCong checkIn=new ChiTietChamCong();
            checkIn.setEmployeeId(request.getMaNV());
            checkIn.setDate(LocalDate.now());
            checkIn.setCheckIn(LocalTime.now());
            chiTietChamCongRepository.save(checkIn);
            System.out.println(LocalDate.now());
            ChamCong chamCong=chamCongService.layChamCongTheoWorkDate(LocalDate.now(),request.getMaNV());
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
            }else{
                tinhThoiGianLamViec(request.getMaNV());
            }
            request.setTrangThai("Đã duyệt");
            request.setNguoiDuyet(adminRepository.findById(user).get().getMaAdmin());
            requestRepository.save(request);
        }else{
            if(request.getLoai().equals("Overtime")){
                System.out.println(request.getLoai());
                String[] data = request.getGhiChu().split("\n");
                String ngay = data[0];
                LocalDate convertedDate = LocalDate.parse(ngay);
                ChamCong chamCong=chamCongService.layChamCongTheoWorkDate(convertedDate,request.getMaNV());
                if(chamCong == null){
                    chamCong=new ChamCong();
                    chamCong.setEmployeeId(request.getMaNV());
                    chamCong.setWorkDate(convertedDate);
                    chamCong.setIsEarly(false);
                    chamCong.setIsLate(false);
                    chamCong.setIsPaidLeaveDay(false);
                }
                System.out.println(chamCong.getWorkDate()+"  "+chamCong.getIsOverTime() );
                chamCong.setIsOverTime(true);
                chamCongService.luuChamCong(chamCong);
                request.setTrangThai("Đã duyệt");
                request.setNguoiDuyet(adminRepository.findById(user).get().getMaAdmin());
                requestRepository.save(request);
            }else{
                String[] data = request.getGhiChu().split("\n");
                String loaiNghiPhep = data[0];
                String ngayBatDau = data[1];
                String ngayKetThuc = data[2];
                System.out.println(loaiNghiPhep+" "+ngayBatDau+" "+ngayKetThuc);
                int soNgayDaNghiPhep=chamCongService.countPaidLeaveDay(request.getMaNV(),LocalDate.now().getYear());
                System.out.println(chamCongService.countPaidLeaveDay(request.getMaNV(),LocalDate.now().getYear()));
                // Parse the input strings to LocalDate
                LocalDate startDate = LocalDate.parse(ngayBatDau);
                LocalDate endDate = LocalDate.parse(ngayKetThuc);
                // Create a list to store the dates
                List<LocalDate> dates = new ArrayList<>();
                List<ChamCong> chamCongs = new ArrayList<>();
                // Use a for loop to iterate from startDate to endDate
                for (LocalDate date = startDate; !date.isAfter(endDate); date = date.plusDays(1)) {
                    dates.add(date);
                    System.out.println(date);
                    ChamCong chamCong=chamCongService.layChamCongTheoWorkDate(date,request.getMaNV());
                    if(chamCong == null){
                        chamCong=new ChamCong();
                        chamCong.setEmployeeId(request.getMaNV());
                        chamCong.setWorkDate(date);
                        chamCong.setIsEarly(false);
                        chamCong.setIsLate(false);
                        chamCong.setIsPaidLeaveDay(false);
                        chamCong.setIsApprovedLeaveDay(false);
                        chamCong.setWorkingHours(0.0);
                        chamCong.setOverTimeHour(0.0f);
                    }
                    chamCongs.add(chamCong);
                }
                if(loaiNghiPhep.equals("Nghỉ phép năm")){
                    if(soNgayDaNghiPhep+chamCongs.size()>globalVariables.getMaxNghiPhep()){
                        model.addAttribute("isFail", "1");
                        model.addAttribute("message","Không thể duyệt vì vượt quá số ngày nghỉ phép tối đa");
                    }else{
                        for(ChamCong chamCong : chamCongs){
                            chamCong.setIsPaidLeaveDay(true);
                        }
                    }
                }else{
                    for(ChamCong chamCong : chamCongs){
                        chamCong.setIsApprovedLeaveDay(true);
                    }
                }
                request.setTrangThai("Đã duyệt");
                request.setNguoiDuyet(adminRepository.findById(user).get().getMaAdmin());
                requestRepository.save(request);
            }
        }
        List<Request> requests=requestRepository.findAllByTrangThai("Chưa xử lí");
        for(Request i : requests){
            i.setTen(nhanVienService.layNhanVienTheoId(i.getMaNV()).getHoTen());
        }
        System.out.println(requests.size());
        model.addAttribute("requests", requests);
        model.addAttribute("active", "yeuCau");
        return "request_admin";
    }

    @PostMapping("/tuChoi")
    public String tuChoi(@CookieValue(value = "ma") String user, Model model, @RequestParam String requestId){
        System.out.println(requestId);
        Request request = requestRepository.findById(Integer.parseInt(requestId)).get();
        request.setTrangThai("Từ chối");
        request.setNguoiDuyet(adminRepository.findById(user).get().getMaAdmin());
        requestRepository.save(request);
        System.out.println(adminRepository.findById(user).get().getHoTen());
        return "redirect:/requests/admin";
    }


    public double tinhThoiGianLamViec(String manv){
        ChamCong chamCong = chamCongService.layChamCongTheoWorkDate(LocalDate.now(),manv);
        float overTimeHour=0;
        double workHour=0;
        // Fetch records for today, sorted by time
        List<ChiTietChamCong> records = chiTietChamCongRepository.layChiTietChamCong(manv);
        List<Duration> durations = new ArrayList<>();
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime cutoffTime = LocalTime.of(17, 30);
        LocalTime lunchStart = LocalTime.of(12, 0);
        LocalTime lunchEnd = LocalTime.of(13, 0);
        System.out.println(records.size());
        // Iterate through the records to calculate the time difference
        for (int i = 1; i < records.size(); i++) {
            ChiTietChamCong previous = records.get(i - 1);
            ChiTietChamCong current = records.get(i);

            // Get the time from checkIn or checkOut
            LocalTime previousTime = previous.getCheckIn() != null ? previous.getCheckIn() : previous.getCheckOut();
            LocalTime currentTime = current.getCheckIn() != null ? current.getCheckIn() : current.getCheckOut();

            // Skip if both previous and current are checkOut
            if (previous.getCheckOut() != null) {
                continue;
            }
            // Check if the current time is null and set it to the cutoff time
            if (currentTime == null || (currentTime.isAfter(cutoffTime)&&chamCong.getIsOverTime()==false)) {
                currentTime = cutoffTime;
            }
            // Skip if the current time is not after the previous time
            if (!currentTime.isAfter(previousTime)) {
                continue;
            }
            if(previousTime.isAfter(lunchStart) && currentTime.isBefore(lunchEnd)){
                continue;
            }
            System.out.println(currentTime+" "+cutoffTime);
            System.out.println(currentTime.isAfter(cutoffTime));

            // If isOvertime is true, only calculate time after 17:30
            if (chamCong.getIsOverTime()&&currentTime.isAfter(cutoffTime)) {
                if (!previousTime.isAfter(cutoffTime)) {
                    // If previous time is before or at 17:30, move it to 17:30
                    System.out.println(previousTime+" "+cutoffTime);
                    Duration duration = Duration.between(previousTime, cutoffTime);
                    // If time spans across lunch break, exclude it
                    if (!cutoffTime.isBefore(lunchStart) && !previousTime.isAfter(lunchEnd)) {
                        // Calculate overlapping time with lunch
                        LocalTime adjustedStart = previousTime.isBefore(lunchStart) ? lunchStart : previousTime;
                        LocalTime adjustedEnd = cutoffTime.isAfter(lunchEnd) ? lunchEnd : cutoffTime;
                        Duration lunchOverlap = Duration.between(adjustedStart, adjustedEnd);
                        // Subtract lunch overlap from the total duration
                        duration = duration.minus(lunchOverlap);
                    }
                    workHour+=duration.toMinutes()/60.0f;
                    System.out.println(duration.toHours()+"-"+duration.toMinutes()+" "+duration.toSeconds());

                    duration = Duration.between(cutoffTime, currentTime);

                    overTimeHour+=duration.toMinutes()/60.0f;
                    System.out.println(duration.toHours()+" "+duration.toMinutes()+" "+duration.toSeconds());
                    continue;
                }

                // Skip if the adjusted previous time is not before the current time
                if (!previousTime.isBefore(currentTime)) {
                    continue;
                }
            }
            // Adjust previous time if it is before 08:00
            if (previousTime.isBefore(startTime)) {
                previousTime = startTime;
            }

            // Calculate the duration and add to the list
            Duration duration = Duration.between(previousTime, currentTime);
            // If time spans across lunch break, exclude it
            if (!currentTime.isBefore(lunchStart) && !previousTime.isAfter(lunchEnd)) {
                // Calculate overlapping time with lunch
                LocalTime adjustedStart = previousTime.isBefore(lunchStart) ? lunchStart : previousTime;
                LocalTime adjustedEnd = currentTime.isAfter(lunchEnd) ? lunchEnd : currentTime;
                Duration lunchOverlap = Duration.between(adjustedStart, adjustedEnd);
                // Subtract lunch overlap from the total duration
                duration = duration.minus(lunchOverlap);
            }
            durations.add(duration);
            workHour+=duration.toMinutes()/60.0f;
            System.out.println(duration.toHours()+"  "+duration.toMinutes());
        }
        System.out.println(workHour+"    "+overTimeHour);
        System.out.println(chamCong.getId());
        chamCong.setWorkingHours(workHour);
        chamCong.setOverTimeHour(overTimeHour);
        chamCongService.luuChamCong(chamCong);
        return workHour;
    }
}
