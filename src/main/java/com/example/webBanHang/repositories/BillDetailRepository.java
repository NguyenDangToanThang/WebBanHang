package com.example.webBanHang.repositories;

import com.example.webBanHang.model.Bill_Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BillDetailRepository extends JpaRepository<Bill_Detail , Long> {
    List<Bill_Detail> findByBillId(Long bill_id);
}
