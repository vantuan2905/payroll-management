package org.example.payroll_management.dao;

import org.example.payroll_management.model.KhauTru;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KhauTruRepository extends JpaRepository<KhauTru, Long> {
    List<KhauTru> findAllByMaPBAndMaCV(String maPB, String maCV);
}
