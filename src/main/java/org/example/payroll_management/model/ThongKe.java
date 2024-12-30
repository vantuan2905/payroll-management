package org.example.payroll_management.model;

public class ThongKe {
    public double thoiGianLamViec;
    public int diSomVeMuon;
    public int nghiPhep;
    public int nghiKhongLuong;
    public int nghiKhongPhep;
    public int ot;
    public int nghiLe;

    public ThongKe(double thoiGianLamViec, int diSomVeMuon, int nghiPhep, int nghiKhongLuong, int nghiKhongPhep, int ot, int nghiLe) {
        this.thoiGianLamViec = thoiGianLamViec;
        this.diSomVeMuon = diSomVeMuon;
        this.nghiPhep = nghiPhep;
        this.nghiKhongLuong = nghiKhongLuong;
        this.nghiKhongPhep = nghiKhongPhep;
        this.ot = ot;
        this.nghiLe = nghiLe;
    }
}
