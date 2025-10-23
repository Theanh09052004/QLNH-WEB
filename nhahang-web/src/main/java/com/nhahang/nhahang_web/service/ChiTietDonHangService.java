package com.nhahang.nhahang_web.service;

import com.nhahang.nhahang_web.model.ChiTietDonHang;
import com.nhahang.nhahang_web.model.DonHang;

import java.util.List;

public interface ChiTietDonHangService {
    List<ChiTietDonHang> getAll();
    List<ChiTietDonHang> getByDonHang(DonHang donHang);
    void save(ChiTietDonHang chiTietDonHang);
    ChiTietDonHang saveAndReturn(ChiTietDonHang chiTietDonHang);
    void deleteById(int id);
    ChiTietDonHang getById(int id);
    double tinhTongTien(DonHang donHang);
}
