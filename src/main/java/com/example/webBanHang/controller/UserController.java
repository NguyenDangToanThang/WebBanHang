package com.example.webBanHang.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.webBanHang.dto.ProductDto;
import com.example.webBanHang.dto.UserDto;
import com.example.webBanHang.model.User;
import com.example.webBanHang.service.customer.UserService;
import com.example.webBanHang.service.product.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private Cloudinary cloudinary;

    private Map upload(UserDto userDto) throws IOException {
        Map r = this.cloudinary.uploader().upload(userDto.getAvatar().getBytes(),
                ObjectUtils.asMap("resource_type","auto"));
        return r;
    }

    @GetMapping("/register")
    public String getRegistrationPage(@ModelAttribute("user") UserDto userDto){
        return "register";
    }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute("user") User user , Model model, HttpServletRequest request) {
        user.setRole("USER");
        userService.save(user);
        model.addAttribute("message", "Registered Successfully!!");
        return "register";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/user-page")
    public String userPage (Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        model.addAttribute("products", productService.getAllProducts());
        return "user";
    }
    @GetMapping("/admin-page")
    public String adminPage (Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user",userDetails);
        model.addAttribute("products", productService.getAllProducts());
        return "admin";
    }

    @Secured("ADMIN")
    @GetMapping("/admin-page/account")
    public String account_manager(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        model.addAttribute("users", userService.getAllUser());
        return "account/manager";
    }

    @Secured("ADMIN")
    @GetMapping("/admin-page/account/detail/{id}")
    public String account_detail(@PathVariable Long id, Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        model.addAttribute("account_detail", userService.findById(id));
        return "account/account_detail";
    }

}
