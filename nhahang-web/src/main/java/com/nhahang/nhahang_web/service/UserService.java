package com.nhahang.nhahang_web.service;

import com.nhahang.nhahang_web.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> login(String username, String password);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    void save(User user);
    void resetPassword(String email, String newPassword);

    List<User> getAll();
    List<User> search(String keyword);
    void changeRole(int id);
    Optional<User> getCurrentUser(String username);
    String changePassword(String username, String currentPassword, String newPassword, String confirmPassword);

}
