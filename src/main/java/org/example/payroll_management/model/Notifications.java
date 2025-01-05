package org.example.payroll_management.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Data

@Entity
@Table(name = "Notifications")
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "tenNV")
    private String tenNV;

    @Column(name = "noiDung")
    private String noiDung;

    @Column(name = "trangThai")
    private String trangThai;


    @Column(name = "ngayTao")
    private LocalDate ngayTao;
}
