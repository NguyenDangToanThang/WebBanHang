package com.example.webBanHang.service.billDetail;

import com.example.webBanHang.model.Bill_Detail;
import java.util.List;

public interface BillDetailService {
    void addToBillDetail(Bill_Detail billDetail);
    List<Bill_Detail> getBillDetailByBillId(Long bill_id);
}
