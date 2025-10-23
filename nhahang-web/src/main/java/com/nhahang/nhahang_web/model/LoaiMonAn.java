package com.nhahang.nhahang_web.model;

public class LoaiMonAn {
    private int id;
    private String tenLoai;

    public LoaiMonAn() {
    }

    public LoaiMonAn(int id, String tenLoai) {
        this.id = id;
        this.tenLoai = tenLoai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
