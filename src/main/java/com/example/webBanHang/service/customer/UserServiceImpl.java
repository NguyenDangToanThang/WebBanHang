package com.example.webBanHang.service.customer;

import com.example.webBanHang.model.User;
import com.example.webBanHang.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Override
    public void save(User user) {
        User user_mew = new User(
                user.getEmail(),
                passwordEncoder.encode(user.getPassword()),
                user.getRole(),
                user.getFullname());
        userRepository.save(user_mew);
    }

    @Override
    public void update(User user) {
        if (userRepository.existsById(user.getId()) && user.getId() != null) {
            userRepository.save(user);
        }
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByEmail(username);
    }
}
