package com.nhahang.nhahang_web.serviceimpl;

import com.nhahang.nhahang_web.model.Ban;
import com.nhahang.nhahang_web.service.BanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class BanServiceImpl implements BanService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.base-url}")
    private String apiBaseUrl;

    private String getApiUrl() {
        return apiBaseUrl + "/ban";
    }

    @Override
    public List<Ban> getAll() {
        return Arrays.asList(restTemplate.getForObject(getApiUrl(), Ban[].class));
    }

    @Override
    public Ban getById(int id) {
        return restTemplate.getForObject(getApiUrl() + "/" + id, Ban.class);
    }

    @Override
    public void save(Ban ban) {
        if (ban.getId() == 0) {
            restTemplate.postForObject(getApiUrl(), ban, Ban.class);
        } else {
            restTemplate.put(getApiUrl() + "/" + ban.getId(), ban);
        }
    }

    @Override
    public void delete(int id) {
        restTemplate.delete(getApiUrl() + "/" + id);
    }

    @Override
    public List<Ban> search(String keyword) {
        return Arrays.asList(restTemplate.getForObject(getApiUrl() + "/search?keyword=" + keyword, Ban[].class));
    }

    @Override
    public boolean existsByTenBan(String tenBan) {
        return getAll().stream().anyMatch(b -> b.getTenBan().equalsIgnoreCase(tenBan));
    }

    @Override
    public List<Ban> getBansTrong() {
        String url = getApiUrl() + "/trong"; // Gọi đúng endpoint API bạn đã tạo
        Ban[] bans = restTemplate.getForObject(url, Ban[].class);
        return Arrays.asList(bans);
    }

}
