package com.sharat.fintech_tracker.model;

import jakarta.persistence.*;
import java.time.Month;

@Entity
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private ExpenseCategory category;

    private Month month;
    private int year;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Constructors
    public Budget() {
    }

    public Budget(Double amount, ExpenseCategory category, Month month, int year, User user) {
        this.amount = amount;
        this.category = category;
        this.month = month;
        this.year = year;
        this.user = user;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
