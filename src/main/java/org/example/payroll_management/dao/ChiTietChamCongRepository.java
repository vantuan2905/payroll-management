package org.example.payroll_management.dao;
import org.example.payroll_management.model.ChiTietChamCong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChiTietChamCongRepository extends JpaRepository<ChiTietChamCong,Integer> {
    @Query(nativeQuery = true,value = "SELECT * FROM Chi_Tiet_Cham_Cong WHERE work_date=CURRENT_DATE and employee_id=?1 \n" +
            "ORDER BY \n" +
            "    CASE \n" +
            "        WHEN check_In IS NOT NULL THEN check_In\n" +
            "        ELSE check_Out\n" +
            "    END ASC;")
    List<ChiTietChamCong> layChiTietChamCong(String employeeId);

    List<ChiTietChamCong> findByDate(LocalDate date);
}