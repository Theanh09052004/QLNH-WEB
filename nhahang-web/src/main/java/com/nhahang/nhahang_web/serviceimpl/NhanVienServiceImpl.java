package com.nhahang.nhahang_web.serviceimpl;

import com.nhahang.nhahang_web.model.NhanVien;
import com.nhahang.nhahang_web.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

@Service
public class NhanVienServiceImpl implements NhanVienService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.base-url}")
    private String apiUrl;

    @Override
    public List<NhanVien> getAll() {
        String url = apiUrl + "/nhanvien";
        ResponseEntity<List<NhanVien>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<NhanVien>>() {}
        );
        return response.getBody();
    }

    @Override
    public NhanVien getById(int id) {
        return restTemplate.getForObject(apiUrl + "/nhanvien/" + id, NhanVien.class);
    }

    @Override
    public void save(NhanVien nv) {
        restTemplate.postForObject(apiUrl + "/nhanvien", nv, NhanVien.class);
    }

    @Override
    public void update(NhanVien nv) {
        restTemplate.put(apiUrl + "/nhanvien/" + nv.getId(), nv);
    }

    @Override
    public void delete(int id) {
        restTemplate.delete(apiUrl + "/nhanvien/" + id);
    }

    @Override
    public List<NhanVien> search(String keyword) {
        String url = apiUrl + "/nhanvien/search";

        if (keyword != null && !keyword.trim().isEmpty()) {
            url += "?keyword=" + keyword.trim(); // chỉ thêm ?keyword= nếu có từ khoá
        }

        ResponseEntity<List<NhanVien>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<NhanVien>>() {}
        );

        return response.getBody();
    }


    @Override
    public boolean existsMaNV(String maNV) {
        String url = apiUrl + "/nhanvien/exists/maNV?value=" + maNV;
        return restTemplate.getForObject(url, Boolean.class);
    }

    @Override
    public boolean existsSdt(String sdt) {
        String url = apiUrl + "/nhanvien/exists/sdt?value=" + sdt;
        return restTemplate.getForObject(url, Boolean.class);
    }

    @Override
    public boolean existsEmail(String email) {
        String url = apiUrl + "/nhanvien/exists/email?value=" + email;
        return restTemplate.getForObject(url, Boolean.class);
    }

}
