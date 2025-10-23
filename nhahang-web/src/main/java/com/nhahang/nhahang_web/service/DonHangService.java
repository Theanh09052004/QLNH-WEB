package com.nhahang.nhahang_web.service;

import com.nhahang.nhahang_web.model.DonHang;

import java.util.List;

public interface DonHangService {
    List<DonHang> getAll();
    DonHang getById(int id);
    void save(DonHang donHang);
    void delete(int id);
    String generateMaDH();
    List<DonHang> search(String keyword);
    void thanhToan(int id);

}