package com.nhahang.nhahang_web.model;

import jakarta.validation.constraints.NotBlank;

public class Ban {
    private int id;

    @NotBlank(message = "Tên bàn không được để trống")
    private String tenBan;

    private TrangThaiBan trangThai = TrangThaiBan.TRONG;

    private String ghiChu;

    public enum TrangThaiBan {
        TRONG, DANG_SU_DUNG
    }

    public Ban() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTenBan() { return tenBan; }
    public void setTenBan(String tenBan) { this.tenBan = tenBan; }

    public TrangThaiBan getTrangThai() { return trangThai; }
    public void setTrangThai(TrangThaiBan trangThai) { this.trangThai = trangThai; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ban)) return false;
        Ban ban = (Ban) o;
        return id == ban.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
