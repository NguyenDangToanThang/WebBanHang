package com.example.webBanHang.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.webBanHang.model.Bill;
import com.example.webBanHang.model.Bill_Detail;
import com.example.webBanHang.model.User;
import com.example.webBanHang.service.bill.BillService;
import com.example.webBanHang.service.billDetail.BillDetailService;
import com.example.webBanHang.service.customer.UserService;

@Controller
public class BillController {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private BillService billService;
    @Autowired
    private BillDetailService billDetailService;

    @GetMapping("/user-page/bill")
    public String getBill(Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        User user = userService.findByUsername(userDetails.getUsername());
        List<Bill> bills = billService.getAllBillByUserId(user.getId());
        model.addAttribute("user" , user);
        if(bills.isEmpty()) {
            model.addAttribute("message", "Không có hóa đơn nào");
            return "bill/bill";
        }
        model.addAttribute("bills", bills);
        return "bill/bill";
    }

    @GetMapping("/user-page/bill-detail/{id}")
    public String getBillDetail(@PathVariable("id") Long id, Model model, Principal principal) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal.getName());
        User user = userService.findByUsername(userDetails.getUsername());
        List<Bill_Detail> bill_Details = billDetailService.getBillDetailByBillId(id);
        model.addAttribute("bill_id", id);
        model.addAttribute("user" , user);
        model.addAttribute("bill_details", bill_Details);
        return "bill/bill_detail";
    }
    
    
}
