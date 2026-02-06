package com.sharat.fintech_tracker.service;

import com.sharat.fintech_tracker.dto.MonthlyHistoryDTO;
import com.sharat.fintech_tracker.model.Budget;
import com.sharat.fintech_tracker.model.Expense;
import com.sharat.fintech_tracker.model.Income;
import com.sharat.fintech_tracker.model.User;
import com.sharat.fintech_tracker.repository.BudgetRepository;
import com.sharat.fintech_tracker.repository.ExpenseRepository;
import com.sharat.fintech_tracker.repository.IncomeRepository;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
    public double getTotalExpenses(User user, Month month, int year) {
        return expenseRepository.findByUser(user).stream()
                .filter(e -> e.getDate() != null &&
                        e.getDate().getMonth() == month &&
                        e.getDate().getYear() == year)
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    @Override
    public double getTotalIncome(User user, Month month, int year) {
        // CLEANED: Direct access now that Income uses LocalDate
        return incomeRepository.findByUser(user).stream()
                .filter(i -> i.getDate() != null &&
                        i.getDate().getMonth() == month &&
                        i.getDate().getYear() == year)
                .mapToDouble(Income::getAmount)
                .sum();
    }

    @Override
    public Map<String, Double> getExpensesByCategory(User user, Month month, int year) {
        return expenseRepository.findByUser(user).stream()
                .filter(e -> e.getDate() != null &&
                        e.getDate().getMonth() == month &&
                        e.getDate().getYear() == year)
                .collect(Collectors.groupingBy(
                        e -> e.getCategory().toString(),
                        Collectors.summingDouble(Expense::getAmount)
                ));
    }

    @Override
    public Map<String, Double> getRemainingBudgetByCategory(User user, Month month, int year) {
        List<Budget> budgets = budgetRepository.findByUserAndMonthAndYear(user, month, year);
        Map<String, Double> spent = getExpensesByCategory(user, month, year);
        Map<String, Double> remaining = new HashMap<>();

        for (Budget budget : budgets) {
            String category = budget.getCategory().toString();
            double totalBudget = budget.getAmount();
            double spentAmount = spent.getOrDefault(category, 0.0);
            remaining.put(category, totalBudget - spentAmount);
        }
        return remaining;
    }

    @Override
    public MonthlyHistoryDTO getMonthlyHistory(User user) {
        Map<String, Double> expenseMap = new TreeMap<>();
        Map<String, Double> incomeMap = new TreeMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        // CLEANED: Unified logic for both Income and Expense
        expenseRepository.findByUser(user).stream()
                .filter(e -> e.getDate() != null)
                .forEach(e -> expenseMap.merge(e.getDate().format(formatter), e.getAmount(), Double::sum));

        incomeRepository.findByUser(user).stream()
                .filter(i -> i.getDate() != null)
                .forEach(i -> incomeMap.merge(i.getDate().format(formatter), i.getAmount(), Double::sum));

        return new MonthlyHistoryDTO(expenseMap, incomeMap);
    }
}