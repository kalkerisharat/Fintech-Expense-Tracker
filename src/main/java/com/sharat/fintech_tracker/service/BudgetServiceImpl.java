package com.sharat.fintech_tracker.service;
import com.sharat.fintech_tracker.model.Budget;
import com.sharat.fintech_tracker.model.ExpenseCategory;
import com.sharat.fintech_tracker.model.User;
import com.sharat.fintech_tracker.repository.BudgetRepository;
import com.sharat.fintech_tracker.service.BudgetService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Override
    public Budget createBudget(Budget budget, User user) {
        budget.setUser(user);
        return budgetRepository.save(budget);
    }

    @Override
    public List<Budget> getBudgetsByUser(User user) {
        return budgetRepository.findByUser(user);
    }

    @Override
    public Optional<Budget> getBudgetByCategoryAndMonth(User user, ExpenseCategory category, Month month, int year) {
        return budgetRepository.findByUserAndCategoryAndMonthAndYear(user, category, month, year);
    }

    @Override
    public Budget updateBudget(Long id, Budget updatedBudget, User user) {
        Budget existing = budgetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Budget not found"));

        if (!existing.getUser().getId().equals(user.getId())) {
            throw new SecurityException("You are not authorized to update this budget");
        }

        existing.setAmount(updatedBudget.getAmount());
        existing.setCategory(updatedBudget.getCategory());
        existing.setMonth(updatedBudget.getMonth());
        existing.setYear(updatedBudget.getYear());

        return budgetRepository.save(existing);
    }

    @Override
    public void deleteBudget(Long id, User user) {
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Budget not found"));

        if (!budget.getUser().getId().equals(user.getId())) {
            throw new SecurityException("You are not authorized to delete this budget");
        }

        budgetRepository.delete(budget);
    }
}
