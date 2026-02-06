package com.sharat.fintech_tracker.service;

import com.sharat.fintech_tracker.dto.DashboardResponseDTO;
import com.sharat.fintech_tracker.model.Expense;
import com.sharat.fintech_tracker.model.Income;
import com.sharat.fintech_tracker.model.User;
import com.sharat.fintech_tracker.repository.ExpenseRepository;
import com.sharat.fintech_tracker.repository.IncomeRepository;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

@Service
public class DashboardService {

    private final ExpenseRepository expenseRepository;
    private final IncomeRepository incomeRepository;

    // Using Constructor Injection (Best Practice)
    public DashboardService(ExpenseRepository expenseRepository, IncomeRepository incomeRepository) {
        this.expenseRepository = expenseRepository;
        this.incomeRepository = incomeRepository;
    }

    public DashboardResponseDTO getDashboardSummary(User user) {
        List<Expense> expenses = expenseRepository.findByUser(user);
        List<Income> incomes = incomeRepository.findByUser(user);

        // 1. Total Calculations
        double totalExpense = expenses.stream().mapToDouble(Expense::getAmount).sum();
        double totalIncome = incomes.stream().mapToDouble(Income::getAmount).sum();

        // 2. Monthly Filtering Logic
        YearMonth currentMonth = YearMonth.now();

        double monthlyExpense = expenses.stream()
                .filter(e -> e.getDate() != null && YearMonth.from(e.getDate()).equals(currentMonth))
                .mapToDouble(Expense::getAmount)
                .sum();

        double monthlyIncome = incomes.stream()
                .filter(i -> i.getDate() != null && YearMonth.from(i.getDate()).equals(currentMonth))
                .mapToDouble(Income::getAmount)
                .sum();

        return new DashboardResponseDTO(totalIncome, totalExpense, monthlyIncome, monthlyExpense);
    }
}