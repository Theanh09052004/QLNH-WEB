package com.nhahang.nhahang_web.serviceimpl;

import com.nhahang.nhahang_web.model.NhapHang;
import com.nhahang.nhahang_web.service.NhapHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class NhapHangServiceImpl implements NhapHangService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.base-url}")
    private String apiBaseUrl;

    private String getApiUrl() {
        return apiBaseUrl + "/nhaphang";
    }

    @Override
    public List<NhapHang> getAll() {
        ResponseEntity<List<NhapHang>> response = restTemplate.exchange(
                getApiUrl(), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<NhapHang>>() {});
        return response.getBody();
    }

    @Override
    public NhapHang getById(int id) {
        return restTemplate.getForObject(getApiUrl() + "/" + id, NhapHang.class);
    }

    @Override
    public void save(NhapHang nhapHang) {
        restTemplate.postForObject(getApiUrl(), nhapHang, NhapHang.class);
    }

    @Override
    public void delete(int id) {
        restTemplate.delete(getApiUrl() + "/" + id);
    }
}
