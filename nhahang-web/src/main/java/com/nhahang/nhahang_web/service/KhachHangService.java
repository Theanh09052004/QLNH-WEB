package com.nhahang.nhahang_web.service;

import com.nhahang.nhahang_web.model.KhachHang;
import java.util.List;

public interface KhachHangService {
    List<KhachHang> getAll();
    void add(KhachHang khachHang);
    void update(KhachHang khachHang);
    void delete(int id);
    KhachHang getById(int id);
    
    boolean existsByMaKH(String maKH);
    boolean existsBySdt(String sdt);
    boolean existsByEmail(String email);
    List<KhachHang> search(String keyword);
}
