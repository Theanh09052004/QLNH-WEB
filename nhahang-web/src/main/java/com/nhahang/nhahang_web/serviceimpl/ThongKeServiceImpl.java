package com.nhahang.nhahang_web.serviceimpl;

import com.nhahang.nhahang_web.dto.ThongKeDTO;
import com.nhahang.nhahang_web.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ThongKeServiceImpl implements ThongKeService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.base-url}")
    private String apiBaseUrl; // ví dụ: http://localhost:8085/api

    private String url(String path, String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            return apiBaseUrl + "/thongke/" + path + "?keyword=" + keyword;
        }
        return apiBaseUrl + "/thongke/" + path;
    }

    @Override
    public List<ThongKeDTO> thongKeNgay(String keyword) {
        ResponseEntity<List<ThongKeDTO>> resp = restTemplate.exchange(
                url("ngay", keyword), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ThongKeDTO>>() {});
        return resp.getBody();
    }

    @Override
    public List<ThongKeDTO> thongKeThang(String keyword) {
        ResponseEntity<List<ThongKeDTO>> resp = restTemplate.exchange(
                url("thang", keyword), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ThongKeDTO>>() {});
        return resp.getBody();
    }

    @Override
    public List<ThongKeDTO> thongKeNam(String keyword) {
        ResponseEntity<List<ThongKeDTO>> resp = restTemplate.exchange(
                url("nam", keyword), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ThongKeDTO>>() {});
        return resp.getBody();
    }
}
