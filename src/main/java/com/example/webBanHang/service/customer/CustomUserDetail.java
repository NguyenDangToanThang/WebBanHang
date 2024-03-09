package com.example.webBanHang.service.customer;

import com.example.webBanHang.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetail implements UserDetails {
    private final User user;

    public CustomUserDetail(User user) {
        this.user = user;
    }

    public String getFullname() {
        return user.getFullname();
    }

    public String getAvatar() { return user.getAvatar();}
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(user::getRole);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
