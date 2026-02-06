package com.sharat.fintech_tracker.controller;

import com.sharat.fintech_tracker.model.SavingsGoal;
import com.sharat.fintech_tracker.model.User;
import com.sharat.fintech_tracker.repository.SavingsGoalRepository;
import com.sharat.fintech_tracker.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/savings-goals")
public class SavingsGoalController {

    private final SavingsGoalRepository repository;
    private final UserService userService;

    public SavingsGoalController(SavingsGoalRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @GetMapping
    public List<SavingsGoal> getGoals() {
        User user = userService.getLoggedInUser();
        return repository.findByUser(user);
    }

    @PostMapping
    public SavingsGoal createGoal(@RequestBody SavingsGoal goal) {
        User user = userService.getLoggedInUser();
        goal.setUser(user);
        return repository.save(goal);
    }

    @PutMapping("/{id}/add-funds")
    public ResponseEntity<SavingsGoal> addFunds(@PathVariable Long id, @RequestParam Double amount) {
        User user = userService.getLoggedInUser();
        return repository.findById(id)
                .filter(g -> g.getUser().getId().equals(user.getId()))
                .map(g -> {
                    g.setCurrentAmount(g.getCurrentAmount() + amount);
                    return ResponseEntity.ok(repository.save(g));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
