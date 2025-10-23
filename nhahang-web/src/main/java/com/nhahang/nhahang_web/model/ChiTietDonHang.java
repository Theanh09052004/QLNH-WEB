package com.nhahang.nhahang_web.model;

public class ChiTietDonHang {
    private Integer id;
    private DonHang donHang;
    private MonAn monAn;
    private Integer soLuong;
    private Double donGia;

    public ChiTietDonHang() {
        this.donHang = new DonHang();
        this.monAn = new MonAn();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DonHang getDonHang() {
        return donHang;
    }

    public void setDonHang(DonHang donHang) {
        this.donHang = donHang;
    }

    public MonAn getMonAn() {
        return monAn;
    }

    public void setMonAn(MonAn monAn) {
        this.monAn = monAn;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Double getDonGia() {
        return donGia;
    }

    public void setDonGia(Double donGia) {
        this.donGia = donGia;
    }

    public Double getThanhTien() {
        if (soLuong != null && donGia != null) {
            return soLuong * donGia;
        }
        return 0.0;
    }
}
