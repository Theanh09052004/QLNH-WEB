package com.nhahang.nhahang_web.model;

import jakarta.validation.constraints.*;

public class KhachHang {
    private int id;

    @NotBlank(message = "Mã khách hàng không được để trống")
    private String maKH;

    @NotBlank(message = "Tên khách hàng không được để trống")
    private String tenKH;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "\\d{10}", message = "Số điện thoại phải đúng 10 chữ số")
    private String sdt;

    @NotBlank(message = "Email không được để trống")
    @Pattern(regexp = ".+@gmail\\.com", message = "Email phải đúng định dạng @gmail.com")
    private String email;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String diaChi;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
