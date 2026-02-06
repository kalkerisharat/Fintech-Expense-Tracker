package com.sharat.fintech_tracker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sharat.fintech_tracker.model.IncomeCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class IncomeDTO {

    @NotNull(message = "Category cannot be null")
    private com.sharat.fintech_tracker.model.IncomeCategory category;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private Double amount;

    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    // Getters and Setters

}
