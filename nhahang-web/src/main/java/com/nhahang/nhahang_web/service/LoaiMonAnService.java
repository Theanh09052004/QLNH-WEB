package com.nhahang.nhahang_web.service;

import com.nhahang.nhahang_web.model.LoaiMonAn;
import java.util.List;

public interface LoaiMonAnService {
    List<LoaiMonAn> getAll();
    LoaiMonAn getById(int id);
    void add(LoaiMonAn loai);
    void update(LoaiMonAn loai);
    void delete(int id);
    List<LoaiMonAn> search(String keyword);
    boolean isTenLoaiExists(String tenLoai);
}
