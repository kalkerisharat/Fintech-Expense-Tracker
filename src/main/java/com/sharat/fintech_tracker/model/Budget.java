package com.sharat.fintech_tracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Month;

@Entity
@Table(name = "budgets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private ExpenseCategory category;

    @Enumerated(EnumType.STRING) // Best practice to store Enums as Strings in DB
    private Month month;

    private int year;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private User user;

    // Custom constructor for manual instantiation
    public Budget(Double amount, ExpenseCategory category, Month month, int year, User user) {
        this.amount = amount;
        this.category = category;
        this.month = month;
        this.year = year;
        this.user = user;
    }
}