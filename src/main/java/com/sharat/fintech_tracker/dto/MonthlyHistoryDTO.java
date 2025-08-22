package com.sharat.fintech_tracker.dto;

import java.util.Map;

public class MonthlyHistoryDTO {
    private Map<String, Double> monthlyExpenses;
    private Map<String, Double> monthlyIncomes;

    public MonthlyHistoryDTO(Map<String, Double> monthlyExpenses, Map<String, Double> monthlyIncomes) {
        this.monthlyExpenses = monthlyExpenses;
        this.monthlyIncomes = monthlyIncomes;
    }

    public Map<String, Double> getMonthlyExpenses() {
        return monthlyExpenses;
    }

    public void setMonthlyExpenses(Map<String, Double> monthlyExpenses) {
        this.monthlyExpenses = monthlyExpenses;
    }

    public Map<String, Double> getMonthlyIncomes() {
        return monthlyIncomes;
    }

    public void setMonthlyIncomes(Map<String, Double> monthlyIncomes) {
        this.monthlyIncomes = monthlyIncomes;
    }
}
