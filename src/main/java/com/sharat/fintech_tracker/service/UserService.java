package com.sharat.fintech_tracker.service;

import com.sharat.fintech_tracker.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByEmail(String email);
    User getLoggedInUser();

}
