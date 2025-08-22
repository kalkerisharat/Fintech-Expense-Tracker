package com.sharat.fintech_tracker.dto;

import lombok.Data;

/**
 * AuthRequest is used to capture user credentials (username/email and password) during login.
 */
@Data
public class AuthRequest {
    // Username or Email
    private String username;

    // User's password
    private String password;
}
