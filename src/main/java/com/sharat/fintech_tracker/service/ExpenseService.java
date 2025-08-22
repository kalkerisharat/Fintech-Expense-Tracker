package com.sharat.fintech_tracker.service;
import com.sharat.fintech_tracker.model.Expense;
import com.sharat.fintech_tracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository repo;

    public ExpenseService(ExpenseRepository repo) {
        this.repo = repo;
    }

    public List<Expense> getAllExpenses() {
        return repo.findAll();
    }

    public Expense addExpense(Expense expense) {
        return repo.save(expense);
    }
}

