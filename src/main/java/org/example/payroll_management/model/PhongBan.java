package org.example.payroll_management.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "PhongBan")
@Data
public class PhongBan {
    @Id
    @Column(name = "maPB", nullable = false, length = 10, unique = true)
    private String maPB;

    @Column(name = "tenPB", nullable = false, length = 100)
    private String tenPB;
}

