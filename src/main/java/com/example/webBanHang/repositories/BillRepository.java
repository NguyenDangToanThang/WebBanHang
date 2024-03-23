package com.example.webBanHang.repositories;

import com.example.webBanHang.model.Bill;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill , Long> {
    List<Bill> getBillByUserId(Long user_id);
}
