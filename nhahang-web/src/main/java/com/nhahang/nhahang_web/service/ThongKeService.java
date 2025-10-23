package com.nhahang.nhahang_web.service;

import java.util.List;

import com.nhahang.nhahang_web.dto.ThongKeDTO;

public interface ThongKeService {
    List<ThongKeDTO> thongKeNgay(String keyword);
    List<ThongKeDTO> thongKeThang(String keyword);
    List<ThongKeDTO> thongKeNam(String keyword);
}
