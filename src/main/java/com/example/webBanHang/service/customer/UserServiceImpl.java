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

    @SuppressWarnings("null")
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

    @SuppressWarnings("null")
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    @Override
    public void updateResetPasswordToken(String token, String email) {
        User user = userRepository.findByEmail(email);
        if(user != null) {
            user.setResetPasswordToken(token);
            userRepository.save(user);
        }
    }

    @Override
    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        User user1= userRepository.findByEmail(user.getEmail());
        user1.setPassword(encodedPassword);
        user1.setResetPasswordToken(null);
        userRepository.save(user1);
    }
}
