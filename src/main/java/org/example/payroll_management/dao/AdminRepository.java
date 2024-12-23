package org.example.payroll_management.dao;

import org.example.payroll_management.model.Admin;
import org.example.payroll_management.model.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,String> {
}
