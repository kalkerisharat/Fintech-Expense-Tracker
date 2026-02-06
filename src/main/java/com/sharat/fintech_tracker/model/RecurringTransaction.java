package com.sharat.fintech_tracker.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Table(name = "recurring_transactions")
@Data
public class RecurringTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;
    private String category; // Can be ExpenseCategory or IncomeCategory name

    @Enumerated(EnumType.STRING)
    private TransactionType type; // EXPENSE or INCOME

    @Enumerated(EnumType.STRING)
    private Frequency frequency; // DAILY, WEEKLY, MONTHLY

    private LocalDate nextDueDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public enum TransactionType {
        EXPENSE, INCOME
    }

    public enum Frequency {
        DAILY, WEEKLY, MONTHLY
    }

    // Constructors, Getters, Setters
    public RecurringTransaction() {
    }

    public RecurringTransaction(Double amount, String category, TransactionType type, Frequency frequency,
            LocalDate nextDueDate, User user) {
        this.amount = amount;
        this.category = category;
        this.type = type;
        this.frequency = frequency;
        this.nextDueDate = nextDueDate;
        this.user = user;
    }

}
