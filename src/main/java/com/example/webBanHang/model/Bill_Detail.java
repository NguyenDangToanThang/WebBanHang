package com.example.webBanHang.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bill_details")
public class Bill_Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name_product;
    private double price;
    private int quantity;
    @ManyToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;

    public Bill_Detail(){}

    public Bill_Detail(String name_product, double price, int quantity, Bill bill) {
        this.name_product = name_product;
        this.price = price;
        this.quantity = quantity;
        this.bill = bill;
    }

    public Bill_Detail(Long id, String name_product, double price, int quantity, Bill bill) {
        this.id = id;
        this.name_product = name_product;
        this.price = price;
        this.quantity = quantity;
        this.bill = bill;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName_product() {
        return name_product;
    }

    public void setName_product(String name_product) {
        this.name_product = name_product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
