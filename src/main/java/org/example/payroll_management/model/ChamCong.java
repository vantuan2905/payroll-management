package org.example.payroll_management.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    @Column(name = "workingHour", columnDefinition = "DOUBLE PRECISION  DEFAULT 0")
    private Double workingHours;

    @Column(name = "isLate", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isLate;

    @Column(name = "isEarly", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isEarly;

    @Column(name = "isOverTime", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isOverTime;

    @Column(name = "overTimeHour", columnDefinition = "FLOAT DEFAULT 0")
    private Float overTimeHour;

    @Column(name = "holiday", columnDefinition = "INTEGER DEFAULT 0")
    private Integer holiday;

    @Column(name = "isPaidLeaveDay", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isPaidLeaveDay;

    @Column(name = "isApprovedLeaveDay", columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isApprovedLeaveDay;
    @Transient
    private OverTime overTime;
}
