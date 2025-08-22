package com.sharat.fintech_tracker.service;

import com.sharat.fintech_tracker.dto.DashboardResponseDTO;
import com.sharat.fintech_tracker.model.Expense;
import com.sharat.fintech_tracker.model.Income;
import com.sharat.fintech_tracker.model.User;
import com.sharat.fintech_tracker.repository.ExpenseRepository;
import com.sharat.fintech_tracker.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.YearMonth;
import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private IncomeRepository incomeRepository;

    public DashboardResponseDTO getDashboardSummary(User user) {
        List<Expense> expenses = expenseRepository.findByUser(user);
        List<Income> incomes = incomeRepository.findByUser(user);

        double totalExpense = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        double totalIncome = incomes.stream()
                .mapToDouble(Income::getAmount)
                .sum();

        YearMonth currentMonth = YearMonth.now();

        double monthlyExpense = expenses.stream()
                .filter(e -> {
                    if (e.getDate() == null) return false;
                    YearMonth expenseMonth = YearMonth.from(e.getDate());
                    return expenseMonth.equals(currentMonth);
                })
                .mapToDouble(Expense::getAmount)
                .sum();

        double monthlyIncome = incomes.stream()
                .filter(i -> {
                    if (i.getDate() == null) return false;
                    YearMonth incomeMonth = YearMonth.from(i.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    return incomeMonth.equals(currentMonth);
                })
                .mapToDouble(Income::getAmount)
                .sum();

        return new DashboardResponseDTO(totalIncome, totalExpense, monthlyIncome, monthlyExpense);
    }
}
