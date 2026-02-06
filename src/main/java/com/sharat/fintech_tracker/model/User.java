package com.sharat.fintech_tracker.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "role")
    private List<Role> roles;

    // ✅ JPA constructor
    public User() {}

    // ✅ Used during registration
    public User(String username, String email, String password, List<Role> roles) {
        this.username = username.toLowerCase();
        this.email = email.toLowerCase();
        this.password = password;
        this.roles = roles;
    }

    // ================= UserDetails =================

    /**
     * 🔑 CRITICAL FIX
     * Spring Security username = EMAIL
     * Must match JWT subject
     */
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    /**
     * ✅ FIXED: Use ArrayList to create mutable list
     * The previous .toList() created an immutable list causing UnsupportedOperationException
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (roles != null) {
            for (Role role : roles) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
            }
        }
        return authorities;
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    // ================= Getters =================

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public List<Role> getRoles() { return roles; }
}