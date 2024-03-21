package com.example.webBanHang.controller;

import com.example.webBanHang.model.Cart;
import com.example.webBanHang.model.Product;
import com.example.webBanHang.model.User;
import com.example.webBanHang.service.cart.CartService;
import com.example.webBanHang.service.customer.UserService;
import com.example.webBanHang.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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
        List<Cart> carts = cartService.getAllProductInCarts(userDetails.getUsername());
        model.addAttribute("user", userDetails);
        model.addAttribute("carts", carts);
        model.addAttribute("total" , cartService.total(carts));
        return "cart/cart";
    }

    @PostMapping("/user-page/addToCart/{id}")
    public String addToCart(
            Model model,
            Principal principal,
            @PathVariable Long id,
            @RequestParam("quantity_cart") int quantity_cart
    ) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", userDetails);
        if(productService.getProductById(id).isPresent()) {
            Product product = productService.getProductById(id).get();
            User user = userService.findByUsername(userDetails.getUsername());
            Cart cart = new Cart();
            List<Cart> carts = cartService.getAllProductInCarts(userDetails.getUsername());
            AtomicBoolean check = new AtomicBoolean(false);
            AtomicInteger temp = new AtomicInteger(quantity_cart);
            carts.forEach((cart1) -> {
                if (Objects.equals(cart1.getProduct().getName(),product.getName())) {
                    cart1.getProduct().setQuantity(temp.get() + cart1.getQuantity_cart());
                    cart.setQuantity_cart(cart1.getProduct().getQuantity());
                    temp.set(cart1.getQuantity_cart());
                    check.set(true);
                }
            });
            if(!check.get()) {
                cart.setQuantity_cart(quantity_cart);
            }
            cart.setProduct(product);
            cart.setUser(user);
            if(check.get()){
                cartService.updateQuantityToCart(product, user, cart);
            } else {
                cartService.addToCart(cart);
            }
            model.addAttribute("message" , "Cart added successfully");
        } else {
            model.addAttribute("message" , "Cart added fail");
        }

        return "redirect:/user-page";
    }

}