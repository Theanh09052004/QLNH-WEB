package com.nhahang.nhahang_web.serviceimpl;

import com.nhahang.nhahang_web.model.NhaCungCap;
import com.nhahang.nhahang_web.service.NhaCungCapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class NhaCungCapServiceImpl implements NhaCungCapService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.base-url}/ncc")
    private String apiUrl;

    @Override
    public List<NhaCungCap> getAll() {
        ResponseEntity<List<NhaCungCap>> response = restTemplate.exchange(
            apiUrl,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<NhaCungCap>>() {}
        );
        return response.getBody();
    }

    @Override
    public NhaCungCap getById(int id) {
        return restTemplate.getForObject(apiUrl + "/" + id, NhaCungCap.class);
    }

    @Override
    public void save(NhaCungCap ncc) {
        try {
            if (ncc.getId() == 0) {
                restTemplate.postForObject(apiUrl, ncc, NhaCungCap.class);
            } else {
                restTemplate.put(apiUrl + "/" + ncc.getId(), ncc);
            }
        } catch (HttpClientErrorException.BadRequest ex) {
            throw new RuntimeException(ex.getResponseBodyAsString());
        }
}

    @Override
    public void delete(int id) {
        restTemplate.delete(apiUrl + "/" + id);
    }

    @Override
    public List<NhaCungCap> search(String keyword) {
        ResponseEntity<List<NhaCungCap>> response = restTemplate.exchange(
            apiUrl + "/search?keyword=" + keyword,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<NhaCungCap>>() {}
        );
        return response.getBody();
    }

}
