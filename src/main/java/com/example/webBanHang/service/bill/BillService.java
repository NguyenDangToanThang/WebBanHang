package com.example.webBanHang.service.bill;

import com.example.webBanHang.model.Bill;

import java.util.List;
import java.util.Optional;

public interface BillService {
    void addToBill(Bill bill);
    Optional<Bill> getBillById(Long id);
    List<Bill> getAllBill();
    List<Bill> getAllBillByUserId(Long user_id);
}
