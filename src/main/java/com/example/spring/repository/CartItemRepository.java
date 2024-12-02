package com.example.spring.repository;

import com.example.spring.models.CartItem;
import com.example.spring.models.Product;
import com.example.spring.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Find all cart items for a user
    List<CartItem> findByUser(User user);

    // Find a cart item by user and product
    CartItem findByUserAndProduct(User user, Product product);
}
