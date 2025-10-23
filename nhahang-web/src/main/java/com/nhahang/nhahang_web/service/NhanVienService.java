package com.nhahang.nhahang_web.service;

import com.nhahang.nhahang_web.model.NhanVien;

import java.util.List;

public interface NhanVienService {
    List<NhanVien> getAll();
    NhanVien getById(int id);
    void save(NhanVien nv);
    void update(NhanVien nv);
    void delete(int id);
    List<NhanVien> search(String keyword);
    boolean existsMaNV(String maNV);
    boolean existsSdt(String sdt);
    boolean existsEmail(String email);

}
