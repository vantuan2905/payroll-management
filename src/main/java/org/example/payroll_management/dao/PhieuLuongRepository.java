package org.example.payroll_management.dao;

import org.example.payroll_management.model.NhanVien;
import org.example.payroll_management.model.PhieuLuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhieuLuongRepository extends JpaRepository<PhieuLuong, Long> {
        PhieuLuong getPhieuLuongByThangAndNamAndManv(int thang, int nam, String manv);
        List<PhieuLuong> findAllByThangAndNam(int thang, int nam);
}
