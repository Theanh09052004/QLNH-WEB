package com.nhahang.nhahang_web.dto;

import com.nhahang.nhahang_web.model.Ban;
import com.nhahang.nhahang_web.model.KhachHang;
import com.nhahang.nhahang_web.model.NhanVien;

import java.util.Date;
import java.util.List;

public class DonHangDTO {
    private int id;
    private String maDH;
    private int idKhachHang;
    private int idNhanVien;
    private double tongTien;
    private Date ngayTao;
    private String trangThai; // “DANG_XU_LY” hoặc “DA_THANH_TOAN”

    // ===== Bàn chính và bàn phụ =====
    private int idBanChinh;          // Giữ tương thích với code cũ
    private List<Integer> idBans;    // Danh sách id bàn được chọn khi thêm mới
    private Ban ban;                 // Thông tin bàn chính khi hiển thị
    private List<Ban> bansPhu;       // Danh sách bàn phụ khi hiển thị

    // ===== Thông tin khách hàng và nhân viên =====
    private KhachHang khachHang;
    private NhanVien nhanVien;

    // ===== Getter & Setter =====
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaDH() {
        return maDH;
    }

    public void setMaDH(String maDH) {
        this.maDH = maDH;
    }

    public int getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(int idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public int getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(int idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getIdBanChinh() {
        return idBanChinh;
    }

    public void setIdBanChinh(int idBanChinh) {
        this.idBanChinh = idBanChinh;
    }

    public List<Integer> getIdBans() {
        return idBans;
    }

    public void setIdBans(List<Integer> idBans) {
        this.idBans = idBans;
    }

    public Ban getBan() {
        return ban;
    }

    public void setBan(Ban ban) {
        this.ban = ban;
    }

    public List<Ban> getBansPhu() {
        return bansPhu;
    }

    public void setBansPhu(List<Ban> bansPhu) {
        this.bansPhu = bansPhu;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }
}
