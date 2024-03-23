package com.example.webBanHang.service.bill;

import com.example.webBanHang.model.Bill;
import com.example.webBanHang.repositories.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillServiceImpl implements BillService{
    @Autowired
    private BillRepository billRepository;
    @SuppressWarnings("null")
    @Override
    public void addToBill(Bill bill) {
        billRepository.save(bill);
    }

    @SuppressWarnings("null")
    @Override
    public Optional<Bill> getBillById(Long id) {
        return billRepository.findById(id);
    }

    @Override
    public List<Bill> getAllBill() {
        return billRepository.findAll();
    }

    @Override
    public List<Bill> getAllBillByUserId(Long user_id) {
        List<Bill> bills = billRepository.getBillByUserId(user_id);
        return bills;
    }
}
