package com.nhahang.nhahang_web.serviceimpl;

import com.nhahang.nhahang_web.model.MonAn;
import com.nhahang.nhahang_web.service.MonAnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.List;

@Service
public class MonAnServiceImpl implements MonAnService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.base-url}/monan")
    private String apiUrl;

    @Override
    public List<MonAn> getAll() {
        return Arrays.asList(restTemplate.getForObject(apiUrl, MonAn[].class));
    }

    @Override
    public void add(MonAn monAn) {
        restTemplate.postForObject(apiUrl, monAn, MonAn.class);
    }

    @Override
    public void update(MonAn monAn) {
        restTemplate.put(apiUrl + "/" + monAn.getId(), monAn);
    }

    @Override
    public void delete(int id) {
        restTemplate.delete(apiUrl + "/" + id);
    }

    @Override
    public MonAn getById(int id) {
        return restTemplate.getForObject(apiUrl + "/" + id, MonAn.class);
    }

    @Override
    public boolean existsByTenMon(String tenMon) {
        List<MonAn> list = getAll();
        return list.stream().anyMatch(m -> m.getTenMon().equalsIgnoreCase(tenMon));
    }

    @Override
    public List<MonAn> search(String keyword) {
        if (keyword == null || keyword.isEmpty()) return getAll();
        return Arrays.asList(restTemplate.getForObject(apiUrl + "/search?keyword=" + keyword, MonAn[].class));
    }
}
