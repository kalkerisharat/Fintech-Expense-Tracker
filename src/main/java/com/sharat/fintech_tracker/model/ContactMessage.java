package com.sharat.fintech_tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id; // <--- MUST BE THIS ONE
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class ContactMessage {

    @Id // <--- This defines the Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments in DB
    private Long id;

    private String name;
    private String email;
    private String message;
    private LocalDateTime sentAt = LocalDateTime.now();
}