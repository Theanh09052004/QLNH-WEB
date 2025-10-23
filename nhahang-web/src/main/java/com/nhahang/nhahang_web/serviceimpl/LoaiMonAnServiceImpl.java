package com.nhahang.nhahang_web.serviceimpl;

import com.nhahang.nhahang_web.model.LoaiMonAn;
import com.nhahang.nhahang_web.service.LoaiMonAnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
public class LoaiMonAnServiceImpl implements LoaiMonAnService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.base-url}")
    private String apiBaseUrl;

    @Override
    public List<LoaiMonAn> getAll() {
        ResponseEntity<LoaiMonAn[]> response = restTemplate.getForEntity(apiBaseUrl + "/loaimonan", LoaiMonAn[].class);
        return Arrays.asList(response.getBody());
    }

    @Override
    public LoaiMonAn getById(int id) {
        return restTemplate.getForObject(apiBaseUrl + "/loaimonan/" + id, LoaiMonAn.class);
    }

    @Override
    public void add(LoaiMonAn loai) {
        restTemplate.postForObject(apiBaseUrl + "/loaimonan", loai, LoaiMonAn.class);
    }

    @Override
    public void update(LoaiMonAn loai) {
        restTemplate.put(apiBaseUrl + "/loaimonan/" + loai.getId(), loai);
    }

    @Override
    public void delete(int id) {
        restTemplate.delete(apiBaseUrl + "/loaimonan/" + id);
    }

    @Override
    public List<LoaiMonAn> search(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAll();
        }
        ResponseEntity<LoaiMonAn[]> response = restTemplate.getForEntity(apiBaseUrl + "/loaimonan/search?keyword=" + keyword, LoaiMonAn[].class);
        return Arrays.asList(response.getBody());
    }

    @Override
    public boolean isTenLoaiExists(String tenLoai) {
        List<LoaiMonAn> list = getAll();
        return list.stream().anyMatch(l -> l.getTenLoai().equalsIgnoreCase(tenLoai));
    }
}
