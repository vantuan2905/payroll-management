package org.example.payroll_management.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "ChamCong")
@Data
public class ChamCong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "employeeId")
    private String employeeId;

    @Column(name = "work_date")
    private LocalDate workDate;

    @Column(name = "checkIn")
    private LocalTime checkIn;

    @Column(name = "checkOut")
    private LocalTime checkOut;

    @Column(name = "workingHour")
    private Double workingHours;

    @Column(name = "isLate")
    private Boolean isLate;

    @Column(name = "isEarly")
    private Boolean isEarly;

    @Column(name = "isOverTime")
    private Boolean isOverTime;

    @Column(name = "overTimeHour")
    private Float overTimeHour;

    @Column(name = "holiday")
    private Integer holiday;

    @Column(name = "isPaidLeaveDay")
    private String isPaidLeaveDay;

}
