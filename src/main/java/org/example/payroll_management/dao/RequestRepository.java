package org.example.payroll_management.dao;

import org.example.payroll_management.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Integer> {

}
