package org.example.payroll_management.dao;

import org.example.payroll_management.model.PhieuLuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuLuongRepository extends JpaRepository<PhieuLuong, Long> {

}
