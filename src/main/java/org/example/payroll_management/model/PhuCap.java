package org.example.payroll_management.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "PhuCap")
@Data
public class PhuCap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "MaPB")
    public String maPB;

    @Column(name = "MaCV")
    public String maCV;

    @Column(name = "TenKhoan")
    public String tenKhoan;

    @Column(name = "MucPhuCap")
    public String mucPhuCap;
}
