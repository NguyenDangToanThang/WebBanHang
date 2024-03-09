package com.example.webBanHang.service.product;

import com.example.webBanHang.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();
    Optional<Product> getProductById(Long id);
    Product add(Product product);
    void update(Product product);
    void delete(Long id);
}
