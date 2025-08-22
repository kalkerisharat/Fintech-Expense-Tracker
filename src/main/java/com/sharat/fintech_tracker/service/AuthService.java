package com.sharat.fintech_tracker.service;

import com.sharat.fintech_tracker.dto.LoginRequest;
import com.sharat.fintech_tracker.dto.AuthResponse;
import com.sharat.fintech_tracker.dto.RegisterRequest;
import com.sharat.fintech_tracker.model.User;
import com.sharat.fintech_tracker.repository.UserRepository;
import com.sharat.fintech_tracker.security.JwtUtil;
import com.sharat.fintech_tracker.model.Role;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Register a new user
    public void register(RegisterRequest request) {
        System.out.println("Attempting to register user with email: " + request.getEmail());

        String normalizedEmail = request.getEmail().toLowerCase();
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        if (request.getRoles() == null || request.getRoles().isEmpty()) {
            request.setRoles(List.of(Role.USER));
        }

        User user = new User(
                request.getUsername(), // optional username field if your model has it
                normalizedEmail,
                encodedPassword,
                request.getRoles()
        );

        userRepository.save(user);
        System.out.println("User registered successfully with email: " + normalizedEmail);
    }

    // Handle user login using email
    public AuthResponse login(LoginRequest request) {
        String email = request.getEmail().toLowerCase();
        System.out.println("Attempting to log in with email: " + email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    System.out.println("User not found: " + email);
                    return new RuntimeException("Invalid email or password");
                });

        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!passwordMatches) {
            System.out.println("Password mismatch for email: " + email);
            throw new RuntimeException("Invalid email or password");
        }

        List<String> roles = user.getRoles().stream()
                .map(Role::name)
                .toList();

        String token = jwtUtil.generateToken(user.getEmail(), roles); // using email as identifier
        System.out.println("Generated JWT token for email: " + email);
        return new AuthResponse(token, user.getEmail(), roles);
    }
}
