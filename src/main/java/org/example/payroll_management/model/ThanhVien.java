package org.example.payroll_management.model;

import jakarta.persistence.*;
import lombok.Data;
@MappedSuperclass
@Data
public class ThanhVien {
    @Column(name = "HoTen")
    public String hoTen;

    @Column(name = "GioiTinh")
    protected String gioiTinh;

    @Column(name = "NgaySinh")
    protected String ngaySinh;

    @Column(name = "Username")
    protected String username;

    @Column(name = "Password")
    protected String password;

    @Column(name = "sdt")
    protected String sdt;

    @Column(name = "Email")
    protected String email;

    @Column(name = "queQuan")
    protected String queQuan;

    @Column(name = "NoiO")
    protected String noiO;

    @Column(name = "urlImg")
    protected String urlImg;
}
