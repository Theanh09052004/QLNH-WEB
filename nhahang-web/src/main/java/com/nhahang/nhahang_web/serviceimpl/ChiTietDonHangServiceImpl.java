package com.nhahang.nhahang_web.serviceimpl;

import com.nhahang.nhahang_web.model.ChiTietDonHang;
import com.nhahang.nhahang_web.model.DonHang;
import com.nhahang.nhahang_web.service.ChiTietDonHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ChiTietDonHangServiceImpl implements ChiTietDonHangService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.base-url}")
    private String apiBaseUrl;

    private String getApiUrl() {
        return apiBaseUrl + "/ctdonhang";
    }

    @Override
    public List<ChiTietDonHang> getAll() {
        ResponseEntity<List<ChiTietDonHang>> response = restTemplate.exchange(
                getApiUrl(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ChiTietDonHang>>() {}
        );
        return response.getBody();
    }

    @Override
    public ChiTietDonHang getById(int id) {
        return restTemplate.getForObject(getApiUrl() + "/" + id, ChiTietDonHang.class);
    }

    @Override
    public List<ChiTietDonHang> getByDonHang(DonHang donHang) {
        int idDonHang = donHang.getId();
        ResponseEntity<List<ChiTietDonHang>> response = restTemplate.exchange(
                getApiUrl() + "/by-donhang/" + idDonHang,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ChiTietDonHang>>() {}
        );
        return response.getBody();
    }

    @Override
    public void save(ChiTietDonHang chiTietDonHang) {
        if (chiTietDonHang.getId() == null || chiTietDonHang.getId() == 0) {
            // Thêm mới
            restTemplate.postForObject(getApiUrl(), chiTietDonHang, ChiTietDonHang.class);
        } else {
            // Cập nhật
            restTemplate.put(getApiUrl() + "/" + chiTietDonHang.getId(), chiTietDonHang);
        }
    }


    @Override
    public ChiTietDonHang saveAndReturn(ChiTietDonHang chiTietDonHang) {
        HttpEntity<ChiTietDonHang> request = new HttpEntity<>(chiTietDonHang);
        ResponseEntity<ChiTietDonHang> response = restTemplate.exchange(
                getApiUrl(),
                HttpMethod.POST,
                request,
                ChiTietDonHang.class
        );
        return response.getBody();
    }

    @Override
    public void deleteById(int id) {
        restTemplate.delete(getApiUrl() + "/" + id);
    }

    @Override
    public double tinhTongTien(DonHang donHang) {
        List<ChiTietDonHang> list = getByDonHang(donHang);
        return list.stream()
                .mapToDouble(ct -> ct.getSoLuong() * ct.getDonGia())
                .sum();
    }
}
