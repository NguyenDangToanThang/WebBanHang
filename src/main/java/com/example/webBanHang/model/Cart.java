package com.example.webBanHang.model;

import jakarta.persistence.*;

@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity_cart;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Cart(){}

    public Cart(Long id, int quantity, Product product, User user) {
        this.id = id;
        this.quantity_cart = quantity;
        this.product = product;
        this.user = user;
    }

    public Cart(int quantity, Product product, User user) {
        this.quantity_cart = quantity;
        this.product = product;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity_cart() {
        return quantity_cart;
    }

    public void setQuantity_cart(int quantity_cart) {
        this.quantity_cart = quantity_cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
