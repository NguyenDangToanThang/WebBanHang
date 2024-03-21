package com.example.webBanHang.service.customer;

import com.example.webBanHang.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void save(User user);
    void update(User user);
    List<User> getAllUser();
    Optional<User> findById(Long id);
    User findByUsername(String username);
    void updateResetPasswordToken(String token , String email);
    User getByResetPasswordToken(String token);
    void updatePassword(User user , String newPassword);
}
