package com.sharat.fintech_tracker.dto;

public class DashboardResponseDTO {
    private double totalIncome;
    private double totalExpense;
    private double monthlyIncome;
    private double monthlyExpense;

    public DashboardResponseDTO(double totalIncome, double totalExpense, double monthlyIncome, double monthlyExpense) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.monthlyIncome = monthlyIncome;
        this.monthlyExpense = monthlyExpense;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public double getMonthlyExpense() {
        return monthlyExpense;
    }

    public void setMonthlyExpense(double monthlyExpense) {
        this.monthlyExpense = monthlyExpense;
    }
}
