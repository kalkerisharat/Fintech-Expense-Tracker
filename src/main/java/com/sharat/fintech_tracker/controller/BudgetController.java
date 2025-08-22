package com.sharat.fintech_tracker.controller;
import com.sharat.fintech_tracker.model.Budget;
import com.sharat.fintech_tracker.model.ExpenseCategory;
import com.sharat.fintech_tracker.model.User;
import com.sharat.fintech_tracker.service.BudgetService;
import com.sharat.fintech_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private UserService userService;

    // ✅ Create new budget
    @PostMapping
    public Budget createBudget(@RequestBody Budget budget, @AuthenticationPrincipal User user) {
        return budgetService.createBudget(budget, user);
    }

    // ✅ Get all budgets for authenticated user
    @GetMapping
    public List<Budget> getBudgets(@AuthenticationPrincipal User user) {
        return budgetService.getBudgetsByUser(user);
    }

    // ✅ Get specific budget by category and month
    @GetMapping("/search")
    public Optional<Budget> getBudgetByCategoryAndMonth(
            @AuthenticationPrincipal User user,
            @RequestParam ExpenseCategory category,
            @RequestParam Month month,
            @RequestParam int year) {
        return budgetService.getBudgetByCategoryAndMonth(user, category, month, year);
    }

    // ✅ Update budget
    @PutMapping("/{id}")
    public Budget updateBudget(@PathVariable Long id, @RequestBody Budget updatedBudget,
                               @AuthenticationPrincipal User user) {
        return budgetService.updateBudget(id, updatedBudget, user);
    }

    // ✅ Delete budget
    @DeleteMapping("/{id}")
    public void deleteBudget(@PathVariable Long id, @AuthenticationPrincipal User user) {
        budgetService.deleteBudget(id, user);
    }
}
