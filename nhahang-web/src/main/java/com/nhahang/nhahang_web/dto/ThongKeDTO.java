package com.nhahang.nhahang_web.dto;

public class ThongKeDTO {
    private String thoiGian;
    private Double tongDoanhThu;
    private Double tongNhapHang;
    private Double loiNhuan;

    public ThongKeDTO() {}

    public String getThoiGian() { return thoiGian; }
    public void setThoiGian(String thoiGian) { this.thoiGian = thoiGian; }

    public Double getTongDoanhThu() { return tongDoanhThu; }
    public void setTongDoanhThu(Double tongDoanhThu) { this.tongDoanhThu = tongDoanhThu; }

    public Double getTongNhapHang() { return tongNhapHang; }
    public void setTongNhapHang(Double tongNhapHang) { this.tongNhapHang = tongNhapHang; }

    public Double getLoiNhuan() { return loiNhuan; }
    public void setLoiNhuan(Double loiNhuan) { this.loiNhuan = loiNhuan; }
}
