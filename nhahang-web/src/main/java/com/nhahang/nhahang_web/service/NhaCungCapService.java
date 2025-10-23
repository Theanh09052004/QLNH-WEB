package com.nhahang.nhahang_web.service;

import com.nhahang.nhahang_web.model.NhaCungCap;

import java.util.List;

public interface NhaCungCapService {
    List<NhaCungCap> getAll();
    NhaCungCap getById(int id);
    void save(NhaCungCap nhaCungCap);
    void delete(int id);
    List<NhaCungCap> search(String keyword);
}
