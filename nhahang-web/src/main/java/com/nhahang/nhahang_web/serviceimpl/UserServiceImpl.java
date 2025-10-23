package com.nhahang.nhahang_web.serviceimpl;

import com.nhahang.nhahang_web.model.User;
import com.nhahang.nhahang_web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.base-url}")
    private String apiUrl;

    @Override
    public Optional<User> login(String username, String password) {
        String url = apiUrl + "/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = Map.of("username", username, "password", password);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<User> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    User.class);
            return Optional.ofNullable(response.getBody());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            String url = apiUrl + "/check-username?username=" + username;
            ResponseEntity<User> response = restTemplate.getForEntity(url, User.class);
            return Optional.ofNullable(response.getBody());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            String url = apiUrl + "/check-email?email=" + email;
            ResponseEntity<User> response = restTemplate.getForEntity(url, User.class);
            return Optional.ofNullable(response.getBody());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(User user) {
        String url = apiUrl + "/register";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<User> request = new HttpEntity<>(user, headers);
        restTemplate.postForEntity(url, request, String.class);
    }

    // ✅ Hàm reset mật khẩu gọi đúng API
    @Override
    public void resetPassword(String email, String newPassword) {
        String url = apiUrl + "/reset-password";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = Map.of(
                "email", email,
                "newPassword", newPassword);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        restTemplate.postForEntity(url, request, Void.class);
    }

    @Override
    public List<User> getAll() {
        String url = apiUrl + "/taikhoan";
        User[] users = restTemplate.getForObject(url, User[].class);
        return Arrays.asList(users);
    }

    @Override
    public List<User> search(String keyword) {
        String url = apiUrl + "/taikhoan/search?keyword=" + keyword;
        User[] users = restTemplate.getForObject(url, User[].class);
        return Arrays.asList(users);
    }

    @Override
    public void changeRole(int id) {
        String url = apiUrl + "/taikhoan/role/" + id;
        restTemplate.put(url, null);
    }

    @Override
    public Optional<User> getCurrentUser(String username) {
        try {
            String url = apiUrl + "/taikhoan/current?username=" + username;
            ResponseEntity<User> response = restTemplate.getForEntity(url, User.class);
            return Optional.ofNullable(response.getBody());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public String changePassword(String username, String currentPassword, String newPassword, String confirmPassword) {
        try {
            String url = apiUrl + "/taikhoan/change-password?username=" + username;
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> body = Map.of(
                    "currentPassword", currentPassword,
                    "newPassword", newPassword,
                    "confirmPassword", confirmPassword
            );

            HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
            return response.getBody();
        } catch (Exception e) {
            return "Lỗi hệ thống khi đổi mật khẩu";
        }
}


}


