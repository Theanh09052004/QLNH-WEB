package com.nhahang.nhahang_web.model;

import java.util.Date;
import java.util.List;

public class DonHang {

    private int id;
    private String maDH;
    private Ban ban;                   // Bàn chính
    private List<Ban> bansPhu;         // Danh sách bàn phụ
    private KhachHang khachHang;
    private NhanVien nhanVien;
    private Date ngayTao;
    private double tongTien;
    private TrangThaiDonHang trangThai;

    private int idBanChinh;               // ID bàn chính
    private List<Integer> idBans;      // Danh sách ID bàn (bao gồm bàn chính và bàn phụ)

    public enum TrangThaiDonHang {
        DANG_XU_LY, DA_THANH_TOAN
    }

    public DonHang() {
        this.ban = new Ban();
        this.khachHang = new KhachHang();
        this.nhanVien = new NhanVien();
    }

    // ===== Getter & Setter =====
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMaDH() { return maDH; }
    public void setMaDH(String maDH) { this.maDH = maDH; }

    public Ban getBan() { return ban; }
    public void setBan(Ban ban) { this.ban = ban; }

    public List<Ban> getBansPhu() { return bansPhu; }
    public void setBansPhu(List<Ban> bansPhu) { this.bansPhu = bansPhu; }

    public KhachHang getKhachHang() { return khachHang; }
    public void setKhachHang(KhachHang khachHang) { this.khachHang = khachHang; }

    public NhanVien getNhanVien() { return nhanVien; }
    public void setNhanVien(NhanVien nhanVien) { this.nhanVien = nhanVien; }

    public Date getNgayTao() { return ngayTao; }
    public void setNgayTao(Date ngayTao) { this.ngayTao = ngayTao; }

    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }

    public TrangThaiDonHang getTrangThai() { return trangThai; }
    public void setTrangThai(TrangThaiDonHang trangThai) { this.trangThai = trangThai; }

    public int getIdBanChinh() { return idBanChinh; }
    public void setIdBanChinh(int idBanChinh) { this.idBanChinh = idBanChinh; }

    public List<Integer> getIdBans() { return idBans; }
    public void setIdBans(List<Integer> idBans) { this.idBans = idBans; }
}
