package com.example.webBanHang.repositories;

import com.example.webBanHang.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long customerId);
    List<Cart> findByProductId(Long productId);
    Cart findByUserIdAndProductId(Long userId, Long productId);
}
