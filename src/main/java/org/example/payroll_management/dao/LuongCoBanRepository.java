package org.example.payroll_management.dao;

import org.example.payroll_management.model.LuongCoBan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LuongCoBanRepository extends JpaRepository<LuongCoBan, Long> {
    LuongCoBan findByMaPBAndMaCV(String maPB, String maCV);
}
