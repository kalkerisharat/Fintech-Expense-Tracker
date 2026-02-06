package com.sharat.fintech_tracker.controller;

import com.sharat.fintech_tracker.dto.IncomeDTO;
import com.sharat.fintech_tracker.model.Income;
import com.sharat.fintech_tracker.model.User;
import com.sharat.fintech_tracker.service.IncomeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/income")
@CrossOrigin(origins = "*")
public class IncomeController {

    private final IncomeService incomeService;

    // Best Practice: Constructor Injection
    public IncomeController(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    @PostMapping
    public ResponseEntity<Income> addIncome(@RequestBody IncomeDTO dto,
                                            @AuthenticationPrincipal User user) {
        // Fix: Added the 'user' argument required by the Service
        return new ResponseEntity<>(incomeService.addIncome(dto, user), HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Income>> addIncomes(@RequestBody List<IncomeDTO> dtos,
                                                   @AuthenticationPrincipal User user) {
        // Fix: Added the 'user' argument required by the Service
        return new ResponseEntity<>(incomeService.addIncomes(dtos, user), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Income>> getAllIncome(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(incomeService.getAll(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id,
                                             @AuthenticationPrincipal User user) {
        // Fix: Pass 'user' so the service can verify ownership before deleting
        incomeService.deleteIncome(id, user);
        return ResponseEntity.noContent().build();
    }
}