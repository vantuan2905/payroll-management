package org.example.payroll_management.dao;

import org.example.payroll_management.model.PhuCap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhuCapRepository extends JpaRepository<PhuCap, Long> {
    List<PhuCap> findAllByMaPBAndMaCV(String maPB, String maCV);
}
