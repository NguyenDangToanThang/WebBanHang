package com.example.webBanHang.repositories;

import com.example.webBanHang.model.Cart;
import com.example.webBanHang.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long customerId);
    List<Cart> findByProductId(Long productId);
    Cart findByUserIdAndProductId(Long userId, Long productId);
}
