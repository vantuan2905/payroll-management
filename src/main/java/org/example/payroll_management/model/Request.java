package org.example.payroll_management.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Request")
@Data
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "loai")
    private String loai;

    @Column(name = "ghiChu")
    private String ghiChu;

    @Column(name = "trangThai")
    private String trangThai;

    @Column(name = "maNV")
    private String maNV;

    @Column(name = "nguoiDuyet")
    private String nguoiDuyet;
}