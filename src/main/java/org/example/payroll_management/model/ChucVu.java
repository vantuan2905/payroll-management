package org.example.payroll_management.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ChucVu")
@Data
public class ChucVu {
    @Id
    @Column(name = "maCV", nullable = false, length = 10, unique = true)
    private String maCV;

    @Column(name = "tenCV", nullable = false, length = 100)
    private String tenCV;
}
