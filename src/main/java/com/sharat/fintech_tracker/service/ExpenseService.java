package com.sharat.fintech_tracker.service;

import com.sharat.fintech_tracker.model.Budget;
import com.sharat.fintech_tracker.model.Expense;
import com.sharat.fintech_tracker.model.User;
import com.sharat.fintech_tracker.repository.BudgetRepository;
import com.sharat.fintech_tracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    private final ExpenseRepository repo;
    private final BudgetRepository budgetRepository;
    private final EmailService emailService;

    public ExpenseService(ExpenseRepository repo, BudgetRepository budgetRepository, EmailService emailService) {
        this.repo = repo;
        this.budgetRepository = budgetRepository;
        this.emailService = emailService;
    }

    public List<Expense> getAllExpenses(User user) {
        return repo.findByUser(user);
    }

    @Transactional
    public Expense addExpense(Expense expense, User user) {
        expense.setUser(user);
        Expense savedExpense = repo.save(expense);
        checkBudgetBreach(savedExpense);
        return savedExpense;
    }

    @Transactional
    public void deleteExpense(Long id, User user) {
        Expense expense = findAndValidateOwnership(id, user);
        repo.delete(expense);
    }

    @Transactional
    public Expense updateExpense(Long id, Expense details, User user) {
        Expense expense = findAndValidateOwnership(id, user);

        expense.setAmount(details.getAmount());
        expense.setCategory(details.getCategory());
        expense.setDate(details.getDate());
        expense.setDescription(details.getDescription());

        return repo.save(expense);
    }

    // Helper to keep code DRY (Don't Repeat Yourself)
    private Expense findAndValidateOwnership(Long id, User user) {
        Expense expense = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with ID: " + id));

        if (!expense.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access Denied: You do not own this expense record.");
        }
        return expense;
    }

    private void checkBudgetBreach(Expense expense) {
        if (expense.getUser() == null || expense.getDate() == null) return;

        Month month = expense.getDate().getMonth();
        int year = expense.getDate().getYear();

        Optional<Budget> budgetOpt = budgetRepository
                .findByUserAndCategoryAndMonthAndYear(expense.getUser(), expense.getCategory(), month, year);

        if (budgetOpt.isPresent()) {
            Budget budget = budgetOpt.get();

            // Optimization: Filter at the DB level if your repo supports it,
            // or keep the stream logic for smaller datasets.
            double totalSpent = repo.findByUser(expense.getUser()).stream()
                    .filter(e -> e.getCategory() == expense.getCategory() &&
                            e.getDate().getMonth() == month &&
                            e.getDate().getYear() == year)
                    .mapToDouble(Expense::getAmount)
                    .sum();

            if (totalSpent > budget.getAmount()) {
                triggerAlert(expense, budget, totalSpent);
            }
        }
    }

    private void triggerAlert(Expense expense, Budget budget, double totalSpent) {
        String subject = "🚨 Budget Alert: " + expense.getCategory();
        String body = String.format(
                "Warning! You've exceeded your %s budget.\nLimit: %.2f\nTotal Spent: %.2f",
                expense.getCategory(), budget.getAmount(), totalSpent
        );

        // Async email sending is preferred in real apps so it doesn't slow down the save operation
        new Thread(() -> {
            try {
                emailService.sendSimpleEmail(expense.getUser().getEmail(), subject, body);
            } catch (Exception e) {
                System.err.println("Email failed: " + e.getMessage());
            }
        }).start();
    }
}