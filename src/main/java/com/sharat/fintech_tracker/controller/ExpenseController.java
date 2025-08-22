package com.sharat.fintech_tracker.controller;
import com.sharat.fintech_tracker.model.Expense;
import com.sharat.fintech_tracker.service.ExpenseService;
import com.sharat.fintech_tracker.repository.ExpenseRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*") // allow frontend access
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @GetMapping
    public List<Expense> getExpenses() {
        return service.getAllExpenses();
    }

    @PostMapping
    public Expense createExpense(@RequestBody Expense expense) {
        return service.addExpense(expense);
    }
}