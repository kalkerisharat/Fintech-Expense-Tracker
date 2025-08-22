package com.sharat.fintech_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String email;
    private List<String> roles;
}
