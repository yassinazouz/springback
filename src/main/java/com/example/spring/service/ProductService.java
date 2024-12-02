package com.example.spring.service;

import com.example.spring.models.Product;
import com.example.spring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long productId) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID must not be null");
        }
        return productRepository.findById(productId);
    }

    public void deleteProduct(Long id) {
        System.out.println("Attempting to delete product with ID: " + id);
        productRepository.deleteById(id);
    }
}

