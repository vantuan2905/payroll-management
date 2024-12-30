package org.example.payroll_management.controller;

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

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/checkOut")
public class CheckOutController {
    private final GlobalVariables globalVariables;
    @Autowired
    ChamCongService chamCongService;
    @Autowired
    TheNhanVienService theNhanVienService;
    @Autowired
    private ChiTietChamCongRepository chiTietChamCongRepository;
    public CheckOutController(GlobalVariables globalVariables) {
        this.globalVariables = globalVariables;
    }

    @GetMapping("/")
    public String checkOut(@RequestParam String uid) {
        String maNV=theNhanVienService.MaNhanVien(uid);
        ChiTietChamCong checkOut=new ChiTietChamCong();
        checkOut.setEmployeeId(maNV);
        checkOut.setDate(LocalDate.now());
        checkOut.setCheckOut(LocalTime.now());
        chiTietChamCongRepository.save(checkOut);
        LocalTime standardCheckOutTime = LocalTime.parse(globalVariables.getCheckOut());
        ChamCong chamCong=chamCongService.layChamCongTheoWorkDate(LocalDate.now(),maNV);
        chamCong.setCheckOut(checkOut.getCheckOut());
        if(chamCong.getCheckOut().isBefore(standardCheckOutTime)){
            chamCong.setIsEarly(true);
        }else chamCong.setIsEarly(false);
        chamCongService.luuChamCong(chamCong);
        tinhThoiGianLamViec(maNV);
        return maNV;
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
