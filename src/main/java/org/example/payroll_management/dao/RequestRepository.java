package org.example.payroll_management.dao;

import org.example.payroll_management.model.NghiLe;
import org.example.payroll_management.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> findAllByTrangThai(String trangThai);
}
