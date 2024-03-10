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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;

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
    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Secured("ADMIN")
    @GetMapping("/admin-page/account/update/{id}")
    public String account_update_page(@PathVariable Long id, Model model, Principal principal){
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        model.addAttribute("account", userService.findById(id));
        return "account/update";
    }
    @Secured("ADMIN")
    @PostMapping("/account/update")
    public String account_update(@ModelAttribute UserDto userDto,
                                 Model model,
                                 Principal principal,
                                 @RequestParam("id") Long id) throws IOException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        Optional<User> existingUser = userService.findById(id);
        User user = new User();
        user.setId(id);
        user.setEmail(userDto.getEmail());
        if(!existingUser.get().getPassword().equals(userDto.getPassword()))
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPassword(existingUser.get().getPassword());
        user.setRole(userDto.getRole());
        user.setFullname(userDto.getFullname());

        if(!userDto.getAvatar().isEmpty()) {
            if(existingUser.get().getAvatar() != null) {
                String publicId = extractPublicIdFromUrl(existingUser.get().getAvatar());
                Map result = cloudinary.uploader().destroy(publicId,ObjectUtils.emptyMap());
                if(result.get("result").equals("ok"))
                    user.setAvatar((String) upload(userDto).get("secure_url"));
            }
            user.setAvatar((String) upload(userDto).get("secure_url"));

        } else {
            user.setAvatar(existingUser.get().getAvatar());
        }
        userService.update(user);
        return "redirect:/admin-page/account";
    }

    public static String extractPublicIdFromUrl(String url) {
        try {
            String[] parts = url.split("/");
            int publicIdIndex = 7;
            String publicId = parts[publicIdIndex];

            if (publicId.startsWith("v")) {
                publicId = publicId.substring(1);
            }
            if (publicId.contains(".")) {
                publicId = publicId.substring(0, publicId.indexOf("."));
            }
            return publicId;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Invalid URL format: " + url);
            return null;
        }
    }

}
