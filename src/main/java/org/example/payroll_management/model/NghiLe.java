package org.example.payroll_management.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "NghiLe")
@Data
public class NghiLe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "_date")
    private String _date;

    @Column(name = "loai")
    private Boolean loai;

    @Column(name = "moTa")
    private String moTa;
}
