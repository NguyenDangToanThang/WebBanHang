package com.example.webBanHang.service.billDetail;

import com.example.webBanHang.model.Bill_Detail;
import com.example.webBanHang.repositories.BillDetailRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillDetailServiceImpl implements BillDetailService{
    @Autowired
    BillDetailRepository billDetailRepository;
    @SuppressWarnings("null")
    @Override
    public void addToBillDetail(Bill_Detail billDetail) {
        billDetailRepository.save(billDetail);
    }
    @Override
    public List<Bill_Detail> getBillDetailByBillId(Long bill_id) {
        List<Bill_Detail> bill_Details = billDetailRepository.findByBillId(bill_id);
        return bill_Details;
    }
}
