package com.example.webBanHang.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProductDto {
    private String name;
    private double price;
    private String description;
    private int quantity;
    private MultipartFile image;

    public ProductDto() {}

    public ProductDto(String name, double price, String description, MultipartFile image, int quantity) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
