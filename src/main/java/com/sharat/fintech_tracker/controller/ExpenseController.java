package com.sharat.fintech_tracker.controller;

import com.sharat.fintech_tracker.model.Expense;
import com.sharat.fintech_tracker.model.User;
import com.sharat.fintech_tracker.service.ExpenseService;
import com.sharat.fintech_tracker.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expenses")
@CrossOrigin(origins = "*") // In production, replace * with your specific frontend URL
public class ExpenseController {

    private static final Logger logger = LoggerFactory.getLogger(ExpenseController.class);

    private final ExpenseService service;
    private final UserService userService;

    public ExpenseController(ExpenseService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Expense>> getExpenses() {
        User user = userService.getLoggedInUser();
        logger.info("Fetching expenses for user: {}", user.getEmail());
        List<Expense> expenses = service.getAllExpenses(user);
        return ResponseEntity.ok(expenses);
    }

    @PostMapping
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        User user = userService.getLoggedInUser();
        logger.info("Creating new expense for user: {}", user.getEmail());

        // Ensure the ID is null to prevent accidental updates via POST
        expense.setId(null);

        Expense created = service.addExpense(expense, user);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense expense) {
        User user = userService.getLoggedInUser();
        logger.info("Updating expense ID: {} for user: {}", id, user.getEmail());

        Expense updated = service.updateExpense(id, expense, user);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        User user = userService.getLoggedInUser();
        logger.info("Deleting expense ID: {} for user: {}", id, user.getEmail());

        service.deleteExpense(id, user);
        return ResponseEntity.noContent().build(); // Returns 204 No Content
    }
}