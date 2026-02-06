package com.sharat.fintech_tracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "income")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", length = 50)
    private IncomeCategory category;

    private Double amount;
    private String description;

    // LocalDate is the modern standard for JPA 3.x / Hibernate 6.x
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private User user;

    // Manual constructor for quick creation if needed
    public Income(IncomeCategory category, Double amount, LocalDate date, User user) {
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.user = user;
    }
}