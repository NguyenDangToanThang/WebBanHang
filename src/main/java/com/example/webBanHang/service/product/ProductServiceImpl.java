package com.example.webBanHang.service.product;

import com.example.webBanHang.model.Product;
import com.example.webBanHang.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductRepository productRepository;
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product add(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void update(Product product) {
        if (product.getId() != null && productRepository.existsById(product.getId())) {
            productRepository.save(product);
        }
    }
    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
