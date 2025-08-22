package com.sharat.fintech_tracker.service;

import com.sharat.fintech_tracker.model.Role;
import com.sharat.fintech_tracker.model.User;
import com.sharat.fintech_tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Implement loadUserByUsername for Spring Security
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    @Override
    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // email is used as the username
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User createUser(String username, String email, String password) {
        // Create the user with a default role (e.g., USER)
        List<Role> defaultRoles = List.of(Role.USER); // Assign USER role by default
        User newUser = new User(username, email, password, defaultRoles);
        return userRepository.save(newUser);
    }

    // Your custom method to find by email (no change)
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
