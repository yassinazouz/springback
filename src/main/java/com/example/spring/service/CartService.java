package com.example.spring.service;

import com.example.spring.models.CartItem;
import com.example.spring.models.Product;
import com.example.spring.models.User;
import com.example.spring.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    public void addProductToCart(User user, Product product, int quantity) {
        // Logic to add the product to the cart, including the quantity
        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        // Save to the repository
        cartItemRepository.save(cartItem);
    }

    // Get all cart items for a user
    public List<CartItem> getCartItemsByUser(User user) {
        return cartItemRepository.findByUser(user);
    }

    // Remove a product from the user's cart
    public void removeProductFromCart(User user, Product product) {
        CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product);
        if (cartItem != null) {
            cartItemRepository.delete(cartItem);
        }
    }
}
