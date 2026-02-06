package com.sharat.fintech_tracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum ExpenseCategory {
    FOOD,
    RENT,
    UTILITIES,
    TRANSPORT,
    HEALTH,       // Changed from HEALTHCARE to match React
    EDUCATION,    // Added
    SHOPPING,     // Added
    ENTERTAINMENT,
    OTHER
}