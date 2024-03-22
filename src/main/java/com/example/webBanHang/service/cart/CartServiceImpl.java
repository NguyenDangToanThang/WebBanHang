package com.example.webBanHang.service.cart;

import com.example.webBanHang.model.Cart;
import com.example.webBanHang.model.Product;
import com.example.webBanHang.model.User;
import com.example.webBanHang.repositories.CartRepository;
import com.example.webBanHang.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public List<Cart> getAllProductInCarts(String username) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByEmail(username));

        // Handle case where user doesn't exist
        if (userOptional.isEmpty()) {
            return null; // Return empty list
        }

        // Safe access to user object
        User user = userOptional.get();
        return cartRepository.findByUserId(user.getId());
    }

    @SuppressWarnings("null")
    @Override
    public void addToCart(Cart cart) {
        cartRepository.save(cart);
    }

    @SuppressWarnings("null")
    @Override
    public void updateQuantityToCart(Product product, User user, Cart cart) {
        Cart cart1 = cartRepository.findByUserIdAndProductId(user.getId(), product.getId());
//        System.out.println("Đây là thông báo được in ra console");
//        System.out.println(user.getId());
//        System.out.println(product.getId());
//        System.out.println(cart1);
        if(cart1 != null) {
            Long id = cart1.getId();
            if(cartRepository.existsById(id)){
                cart.setId(id);
                cartRepository.save(cart);
            }
        }
    }

    @Override
    public double total(List<Cart> carts) {
        double sum = 0;
        for(Cart cart : carts) {
            sum += cart.getQuantity_cart()*cart.getProduct().getPrice();
        }
        return sum;
    }

    @SuppressWarnings("null")
    @Override
    public void delete(Product product, User user) {
        Cart cart1 = cartRepository.findByUserIdAndProductId(user.getId(), product.getId());
        cartRepository.delete(cart1);
    }


}
