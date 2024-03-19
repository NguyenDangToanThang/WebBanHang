package com.example.webBanHang.controller;

import com.example.webBanHang.model.Cart;
import com.example.webBanHang.service.card.CartService;
import com.example.webBanHang.service.customer.UserService;
import com.example.webBanHang.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @GetMapping("/user-page/cart")
    public String cart(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        model.addAttribute("carts",
                cartService.getAllProductInCarts(userDetails.getUsername()));
//        model.addAttribute("message",cartService.getAllProductInCarts(userDetails.getUsername()) );
        return "cart/cart";
    }

    @GetMapping("/user-page/addToCart/{id}")
    public String addToCart(
            Model model,
            Principal principal,
            @PathVariable Long id
    ) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        Cart cart = new Cart();
        cart.setQuantity_cart(100);
        cart.setProduct(productService.getProductById(id).get());
        cart.setUser(userService.findByUsername(userDetails.getUsername()));
        cartService.addToCart(cart);
        model.addAttribute("message" , "Cart added successfully");
        return "redirect:/user-page";
    }

}
