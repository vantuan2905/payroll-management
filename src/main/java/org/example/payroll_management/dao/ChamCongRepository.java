package org.example.payroll_management.dao;

import org.example.payroll_management.model.ChamCong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ChamCongRepository extends JpaRepository<ChamCong,Integer> {
    @Query(value = "SELECT * FROM Cham_Cong WHERE employee_Id =?1 and EXTRACT(YEAR FROM work_date) = ?2 and EXTRACT(month FROM work_date) = ?3", nativeQuery = true)
    List<ChamCong> layChamCongNhanVienTheoThangVaNam(String employeeId,int year,int month);
    @Query(value = "select * from cham_cong where work_date=?1 and employee_id=?2 ", nativeQuery = true)
    ChamCong findByWorkDateAndEmployeeId(LocalDate workDate, String employeeId);
}