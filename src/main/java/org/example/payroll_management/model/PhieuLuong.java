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

    @Column(name = "Nam")
    private int nam;

    @Column(name = "manv")
    private String manv;

    @Column(name = "tongLuong")
    private Long tongLuong;

    @Column(name = "tongTru")
    private Long tongTru;

    @Column(name = "thucLinh")
    private Long thucLinh;


    @Transient
    private ThongKe thongKe;

    @Transient
    private List<KhauTru> danhSachKhauTru;

    @Transient
    private List<PhuCap> danhSachPhuCap;

    @Transient
    private Long luongCoBan;

}
