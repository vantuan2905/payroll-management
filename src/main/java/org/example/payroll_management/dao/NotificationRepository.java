package org.example.payroll_management.dao;

import org.example.payroll_management.model.Notifications;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notifications,Integer> {
}
