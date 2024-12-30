package org.example.payroll_management.dao;

import org.example.payroll_management.model.OverTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OverTimeRepository extends JpaRepository<OverTime,Long> {
}
