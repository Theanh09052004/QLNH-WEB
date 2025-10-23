package com.nhahang.nhahang_web.model;

import java.time.LocalDate;

public class NhapHang {
    private Integer id;
    private MonAn monAn;
    private Integer soLuong;
    private Double giaNhap;
    private LocalDate ngayNhap;

    public NhapHang() {
        this.monAn = new MonAn();
    }

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public MonAn getMonAn() { return monAn; }
    public void setMonAn(MonAn monAn) { this.monAn = monAn; }

    public Integer getSoLuong() { return soLuong; }
    public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }

    public Double getGiaNhap() { return giaNhap; }
    public void setGiaNhap(Double giaNhap) { this.giaNhap = giaNhap; }

    public LocalDate getNgayNhap() { return ngayNhap; }
    public void setNgayNhap(LocalDate ngayNhap) { this.ngayNhap = ngayNhap; }

    public Double getTongTien() {
        if (soLuong != null && giaNhap != null) return soLuong * giaNhap;
        return 0.0;
    }
}
