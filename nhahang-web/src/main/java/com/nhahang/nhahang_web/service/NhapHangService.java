package com.nhahang.nhahang_web.service;

import com.nhahang.nhahang_web.model.NhapHang;
import java.util.List;

public interface NhapHangService {
    List<NhapHang> getAll();
    NhapHang getById(int id);
    void save(NhapHang nhapHang);
    void delete(int id);
}
