package com.example.webBanHang.service.billDetail;

import com.example.webBanHang.model.Bill_Detail;
import com.example.webBanHang.repositories.BillDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillDetailServiceImpl implements BillDetailService{
    @Autowired
    BillDetailRepository billDetailRepository;
    @Override
    public void addToBillDetail(Bill_Detail billDetail) {
        billDetailRepository.save(billDetail);
    }
}
