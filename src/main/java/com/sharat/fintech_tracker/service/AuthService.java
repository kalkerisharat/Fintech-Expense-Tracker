package com.sharat.fintech_tracker.service;

import com.sharat.fintech_tracker.dto.AuthResponse;
import com.sharat.fintech_tracker.dto.LoginRequest;
import com.sharat.fintech_tracker.dto.RegisterRequest;
import com.sharat.fintech_tracker.model.Role;
import com.sharat.fintech_tracker.model.User;
import com.sharat.fintech_tracker.repository.UserRepository;
import com.sharat.fintech_tracker.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    // ---------------- REGISTER ----------------
    public void register(RegisterRequest request) {

        String email = request.getEmail().toLowerCase();

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        List<Role> roles =
                request.getRoles() == null || request.getRoles().isEmpty()
                        ? List.of(Role.USER)
                        : request.getRoles();

        User user = new User(
                request.getUsername(),
                email,
                passwordEncoder.encode(request.getPassword()),
                roles
        );

        userRepository.save(user);
    }

    // ---------------- LOGIN ----------------
    public AuthResponse login(LoginRequest request) {

        // 🔐 Let Spring Security authenticate
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail().toLowerCase(),
                                request.getPassword()
                        )
                );

        User user = userRepository
                .findByEmail(request.getEmail().toLowerCase())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> roles = user.getRoles()
                .stream()
                .map(Role::name)
                .toList();

        String token = jwtUtil.generateToken(user.getEmail(), roles);

        return new AuthResponse(token, user.getEmail(), roles);
    }
}
