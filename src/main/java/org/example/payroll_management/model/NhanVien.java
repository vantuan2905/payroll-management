package org.example.payroll_management.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "NhanVien")
@Data
public class NhanVien extends ThanhVien{
    @Id
    @Column(name = "MaNV")
    private String maNV;

    @Column(name = "MaPB")
    public String maPB;

    @Column(name = "MaCV")
    public String maCV;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "MaNV", referencedColumnName = "MaNV")
    List<PhieuLuong > DanhSachPhieuLuong = new ArrayList<>();
}
