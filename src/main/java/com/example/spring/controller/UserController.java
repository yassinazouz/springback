package com.example.spring.controller;

import com.example.spring.models.User;
import com.example.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("Username and password are required.");
        }

        // Register the user
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        // Validate username and password
        User user = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        if (user != null) {
            // Create a response containing userId and success message
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful.");
            response.put("userId", user.getId()); // Assuming `getId()` returns the user's ID
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body("Invalid username or password.");
    }
}
