package com.sharat.fintech_tracker.dto;

import com.sharat.fintech_tracker.model.Role;
import lombok.Data;

import java.util.List;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private List<Role> roles; // Optional: defaults to USER if not set
}
