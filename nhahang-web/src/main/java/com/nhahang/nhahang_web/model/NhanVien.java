package com.nhahang.nhahang_web.model;

import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class NhanVien {
    private int id;

    @NotBlank(message = "Mã nhân viên không được để trống")
    private String maNV;

    @NotBlank(message = "Tên nhân viên không được để trống")
    private String tenNV;

    @NotBlank(message = "Chức vụ không được để trống")
    private String chucVu;

    @NotNull(message = "Ngày sinh không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate ngaySinh;

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

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public LocalDate getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        this.ngaySinh = ngaySinh;
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
