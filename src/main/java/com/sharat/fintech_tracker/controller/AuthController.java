package com.sharat.fintech_tracker.controller;

import com.sharat.fintech_tracker.dto.AuthResponse;
import com.sharat.fintech_tracker.dto.ErrorResponse;
import com.sharat.fintech_tracker.dto.LoginRequest;
import com.sharat.fintech_tracker.dto.RegisterRequest;
import com.sharat.fintech_tracker.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            authService.register(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("User registered successfully!");
        } catch (Exception e) {
            ErrorResponse error =
                    new ErrorResponse("Registration Error", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(error);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            ErrorResponse error =
                    new ErrorResponse("Login Error", "Invalid email or password");
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(error);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestHeader(value = "Authorization", required = false) String token) {

        // Stateless JWT → nothing to invalidate server-side
        return ResponseEntity.ok("Logged out successfully!");
    }
}
