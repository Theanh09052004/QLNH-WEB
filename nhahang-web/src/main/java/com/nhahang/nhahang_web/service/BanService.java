package com.nhahang.nhahang_web.service;

import com.nhahang.nhahang_web.model.Ban;

import java.util.List;

public interface BanService {
    List<Ban> getAll();
    Ban getById(int id);
    void save(Ban ban);
    void delete(int id);
    List<Ban> search(String keyword);
    boolean existsByTenBan(String tenBan);
    List<Ban> getBansTrong();  // ✅ thêm dòng này
}
