package com.sharat.fintech_tracker.service;
import com.sharat.fintech_tracker.model.*;
import com.sharat.fintech_tracker.dto.MonthlyHistoryDTO;
import com.sharat.fintech_tracker.repository.*;
import com.sharat.fintech_tracker.service.AnalyticsService;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import com.sharat.fintech_tracker.model.User;


@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private final ExpenseRepository expenseRepository;
    private final IncomeRepository incomeRepository;
    private final BudgetRepository budgetRepository;

    public AnalyticsServiceImpl(ExpenseRepository expenseRepository,
                                IncomeRepository incomeRepository,
                                BudgetRepository budgetRepository) {
        this.expenseRepository = expenseRepository;
        this.incomeRepository = incomeRepository;
        this.budgetRepository = budgetRepository;
    }

    @Override
    public double getTotalExpenses(Long userId, Month month, int year) {
        return expenseRepository.findAll().stream()
                .filter(e -> e.getUser().getId().equals(userId) &&
                        e.getDate().getMonth().equals(month) &&
                        e.getDate().getYear() == year)
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    @Override
    public double getTotalIncome(Long userId, Month month, int year) {
        return incomeRepository.findAll().stream()
                .filter(i -> i.getUser().getId().equals(userId) &&
                        i.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).getMonth().equals(month) &&
                        i.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).getYear() == year)
                .mapToDouble(Income::getAmount)
                .sum();
    }

    @Override
    public Map<String, Double> getExpensesByCategory(Long userId, Month month, int year) {
        return expenseRepository.findAll().stream()
                .filter(e -> e.getUser().getId().equals(userId) &&
                        e.getDate().getMonth().equals(month) &&
                        e.getDate().getYear() == year)
                .collect(Collectors.groupingBy(
                        e -> e.getCategory().toString(),
                        Collectors.summingDouble(Expense::getAmount)
                ));
    }

    @Override
    public Map<String, Double> getRemainingBudgetByCategory(Long userId, Month month, int year) {
        List<Budget> budgets = budgetRepository.findAll().stream()
                .filter(b -> b.getUser().getId().equals(userId) &&
                        b.getMonth().equals(month) &&
                        b.getYear() == year)
                .toList();

        Map<String, Double> spent = getExpensesByCategory(userId, month, year);
        Map<String, Double> remaining = new HashMap<>();

        for (Budget budget : budgets) {
            String category = budget.getCategory().toString();
            double totalBudget = budget.getAmount();
            double spentAmount = spent.getOrDefault(category, 0.0);
            remaining.put(category, totalBudget - spentAmount);
        }

        return remaining;
    }
    public MonthlyHistoryDTO getMonthlyHistory(User user) {
        List<Expense> expenses = expenseRepository.findByUser(user);
        List<Income> incomes = incomeRepository.findByUser(user);

        Map<String, Double> expenseMap = new TreeMap<>();
        Map<String, Double> incomeMap = new TreeMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        for (Expense exp : expenses) {
            if (exp.getDate() == null) continue;
            String key = exp.getDate().format(formatter);
            expenseMap.put(key, expenseMap.getOrDefault(key, 0.0) + exp.getAmount());
        }

        for (Income inc : incomes) {
            if (inc.getDate() == null) continue;
            LocalDate date = inc.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            String key = date.format(formatter);
            incomeMap.put(key, incomeMap.getOrDefault(key, 0.0) + inc.getAmount());
        }

        return new MonthlyHistoryDTO(expenseMap, incomeMap);
    }

}
