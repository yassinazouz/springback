package com.example.spring.controller;

import com.example.spring.models.CartItem;
import com.example.spring.models.Product;
import com.example.spring.models.User;
import com.example.spring.models.CartRequest;
import com.example.spring.service.CartService;
import com.example.spring.service.ProductService;
import com.example.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    // Add a product to the user's cart
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody CartRequest cartRequest) {
        System.out.println("CartRequest received: " + cartRequest);

        User user = userService.getUserById(cartRequest.getUserId());
        Optional<Product> productOptional = productService.getProductById(cartRequest.getProductId());

        if (user == null) {
            System.out.println("User not found for ID: " + cartRequest.getUserId());
            return ResponseEntity.badRequest().body("User not found.");
        }

        if (productOptional.isEmpty()) {
            System.out.println("Product not found for ID: " + cartRequest.getProductId());
            return ResponseEntity.badRequest().body("Product not found.");
        }

        Product product = productOptional.get();
        cartService.addProductToCart(user, product, cartRequest.getQuantity());
        return ResponseEntity.ok("Product added to cart.");
    }


    // Get all items in the user's cart
    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItem>> getUserCart(@PathVariable Long userId) {
        // Use userId from the URL path
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.badRequest().body(null);
        }

        List<CartItem> cartItems = cartService.getCartItemsByUser(user);
        return ResponseEntity.ok(cartItems);
    }

    // Remove a product from the user's cart
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromCart(@RequestBody CartRequest cartRequest) {
        // Use userId from request payload
        User user = userService.getUserById(cartRequest.getUserId());
        Optional<Product> productOptional = productService.getProductById(cartRequest.getProductId());

        if (user == null || productOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User or Product not found.");
        }

        Product product = productOptional.get();

        cartService.removeProductFromCart(user, product);
        return ResponseEntity.ok("Product removed from cart.");
    }
}
