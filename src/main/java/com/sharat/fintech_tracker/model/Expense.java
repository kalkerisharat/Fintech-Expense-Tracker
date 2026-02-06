package com.sharat.fintech_tracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

@Entity
@Table(name = "expenses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", length = 50)
    private ExpenseCategory category;

    private String description;

    private Double amount; // Using Double wrapper for consistency

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY) // Lazy loading is better for performance
    @JoinColumn(name = "user_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private User user;

    // Custom constructor for convenience
    public Expense(ExpenseCategory category, String description, Double amount, LocalDate date, User user) {
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.user = user;
    }
}