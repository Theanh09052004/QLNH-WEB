package com.nhahang.nhahang_web.serviceimpl;

import com.nhahang.nhahang_web.model.KhachHang;
import com.nhahang.nhahang_web.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;

@Service
public class KhachHangServiceImpl implements KhachHangService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.base-url}/khachhang")
    private String apiUrl;

    @Override
    public List<KhachHang> getAll() {
        return Arrays.asList(restTemplate.getForObject(apiUrl, KhachHang[].class));
    }

    @Override
    public void add(KhachHang khachHang) {
        restTemplate.postForObject(apiUrl, khachHang, KhachHang.class);
    }

    @Override
    public void update(KhachHang khachHang) {
        restTemplate.put(apiUrl + "/" + khachHang.getId(), khachHang);
    }

    @Override
    public void delete(int id) {
        restTemplate.delete(apiUrl + "/" + id);
    }

    @Override
    public KhachHang getById(int id) {
        return restTemplate.getForObject(apiUrl + "/" + id, KhachHang.class);
    }

    @Override
    public boolean existsByMaKH(String maKH) {
        return getAll().stream().anyMatch(kh -> kh.getMaKH().equalsIgnoreCase(maKH));
    }

    @Override
    public boolean existsBySdt(String sdt) {
        return getAll().stream().anyMatch(kh -> kh.getSdt().equals(sdt));
    }

    @Override
    public boolean existsByEmail(String email) {
        return getAll().stream().anyMatch(kh -> kh.getEmail().equalsIgnoreCase(email));
    }


    @Override
    public List<KhachHang> search(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return getAll();
        }
        return Arrays.asList(restTemplate.getForObject(apiUrl + "/search?keyword=" + keyword, KhachHang[].class));
    }
}
