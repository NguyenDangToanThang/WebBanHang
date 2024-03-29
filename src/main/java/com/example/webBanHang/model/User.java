package com.example.webBanHang.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String role;
    private String fullname;
    private String avatar;

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @OneToMany(mappedBy = "user")
    Set<Cart> cardSet = new HashSet<Cart>();

    @OneToMany(mappedBy = "user")
    Set<Bill> billSet = new HashSet<>();

    public User() {}

    public User(String email, String password, String role, String fullname) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.fullname = fullname;
    }

    public User(String email, String password, String role, String fullname, String avatar) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.fullname = fullname;
        this.avatar = avatar;
    }
    public User(Long id, String email, String password, String role, String fullname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.fullname = fullname;
    }

    public User(Long id, String email, String password, String role, String fullname, String avatar) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.fullname = fullname;
        this.avatar = avatar;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
