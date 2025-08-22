package com.sharat.fintech_tracker.service;
import com.sharat.fintech_tracker.model.Budget;
import com.sharat.fintech_tracker.model.ExpenseCategory;
import com.sharat.fintech_tracker.model.User;

import java.time.Month;
import java.util.List;
import java.util.Optional;

public interface BudgetService {

    Budget createBudget(Budget budget, User user);

    List<Budget> getBudgetsByUser(User user);

    Optional<Budget> getBudgetByCategoryAndMonth(User user, ExpenseCategory category, Month month, int year);

    Budget updateBudget(Long id, Budget updatedBudget, User user);

    void deleteBudget(Long id, User user);
}
