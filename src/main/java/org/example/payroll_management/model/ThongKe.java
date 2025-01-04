package org.example.payroll_management.model;

public class ThongKe {
    public double thoiGianLamViec;
    public double otNgayThuong;
    public int ot;
    public double otNgayNghi;
    public double otNgayLe;
    public int diMuonVeSom;
    public int nghiPhep;
    public int nghiKhongLuong;
    public int nghiKhongPhep;
    public int nghiLe;
    public double ngayThucTe;
    public int ngayCongChuan;

    // Constructor
    public ThongKe(double thoiGianLamViec, double otNgayThuong, int ot, double otNgayNghi, double otNgayLe,
                   int diSomVeMuon, int nghiPhep, int nghiKhongLuong, int nghiKhongPhep, int nghiLe, int ngayCongChuan) {
        this.thoiGianLamViec = thoiGianLamViec;
        this.otNgayThuong = otNgayThuong;
        this.ot = ot;
        this.otNgayNghi = otNgayNghi;
        this.otNgayLe = otNgayLe;
        this.diMuonVeSom = diSomVeMuon;
        this.nghiPhep = nghiPhep;
        this.nghiKhongLuong = nghiKhongLuong;
        this.nghiKhongPhep = nghiKhongPhep;
        this.nghiLe = nghiLe;
        this.ngayCongChuan = ngayCongChuan;
    }
}
