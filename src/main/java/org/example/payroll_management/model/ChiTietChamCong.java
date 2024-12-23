package org.example.payroll_management.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "ChiTietChamCong")
@Data
public class ChiTietChamCong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "employeeId")
    private String employeeId;

    @Column(name = "work_date")
    private LocalDate date;

    @Column(name = "checkIn")
    private LocalTime checkIn;

    @Column(name = "checkOut")
    private LocalTime checkOut;
}
