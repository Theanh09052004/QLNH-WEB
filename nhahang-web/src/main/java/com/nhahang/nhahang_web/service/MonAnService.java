package com.nhahang.nhahang_web.service;

import com.nhahang.nhahang_web.model.MonAn;
import java.util.List;

public interface MonAnService {
    List<MonAn> getAll();
    void add(MonAn monAn);
    void update(MonAn monAn);
    void delete(int id);
    MonAn getById(int id);
    boolean existsByTenMon(String tenMon);
    List<MonAn> search(String keyword);
}
