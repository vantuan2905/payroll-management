package org.example.payroll_management.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PhieuLuong")
@Data
public class PhieuLuong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "Thang")
    private int thang;

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // Enforces relationship
    @JoinColumn(name = "MaNV", nullable = false)
    private NhanVien nhanVien;

    @Transient
    private List<KhauTru> danhSachKhauTru;

    @Transient
    private List<PhuCap> danhSachPhuCap;

    @Transient
    private Double luongCoBan;

}
