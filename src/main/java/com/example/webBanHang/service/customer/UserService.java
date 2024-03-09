package com.example.webBanHang.service.customer;

import com.example.webBanHang.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void save(User user);
    void update(User user);
    List<User> getAllUser();
    Optional<User> findById(Long id);
}
