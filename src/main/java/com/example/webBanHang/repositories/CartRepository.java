package com.example.webBanHang.repositories;

import com.example.webBanHang.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Card , Long> {
}
