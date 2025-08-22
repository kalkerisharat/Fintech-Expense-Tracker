package com.sharat.fintech_tracker.controller;

import com.sharat.fintech_tracker.dto.*;
import com.sharat.fintech_tracker.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest request) {
        try {
            authService.register(request);
            return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED); // 201 status
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("Registration Error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST); // 400 status
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            System.out.println("Login successful, token: " + response.getToken()); // <- add this
            return new ResponseEntity<>(response, HttpStatus.OK); // 200 status
        } catch (RuntimeException e) {
            System.out.println("Login failed: " + e.getMessage()); // <- and this
            ErrorResponse error = new ErrorResponse("Login Error", "Invalid username or password");
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED); // 401 status
        }
    }
    // AuthController.java
    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@RequestHeader("Authorization") String token) {
        try {
            // Here you could implement token invalidation/blacklisting if required
            System.out.println("User logged out. Token: " + token);

            return new ResponseEntity<>("Logged out successfully!", HttpStatus.OK);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("Logout Error", e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

}

