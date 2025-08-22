package com.sharat.fintech_tracker.controller;

import com.sharat.fintech_tracker.model.Role;
import com.sharat.fintech_tracker.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class ProfileController {

    @GetMapping("/profile")
    public User getProfile() {
        // Get the authenticated user from the security context
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Assume you have a UserService to fetch user details by username (optional)
        String username = userDetails.getUsername();

        // Create a mock user with default roles
        List<Role> roles = List.of(Role.USER);  // Or whatever roles you'd like

        // Return the user with username, email, password, and roles
        return new User(username, "user@example.com", "some-encoded-password", roles);
    }
}
