package com.example.webBanHang.service.cart;

import com.example.webBanHang.model.Cart;
import com.example.webBanHang.model.Product;
import com.example.webBanHang.model.User;

import java.util.List;

public interface CartService {
    List<Cart> getAllProductInCarts(String username);
    void addToCart(Cart cart);
    void updateQuantityToCart(Product product,User user, Cart cart);
    void pay();
    double total(List<Cart> carts);
}
