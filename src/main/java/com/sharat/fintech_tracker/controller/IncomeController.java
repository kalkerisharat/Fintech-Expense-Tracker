package com.sharat.fintech_tracker.controller;

import com.sharat.fintech_tracker.dto.IncomeDTO;
import com.sharat.fintech_tracker.model.Income;
import com.sharat.fintech_tracker.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/income")
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @PostMapping
    public ResponseEntity<Income> addIncome(@RequestBody IncomeDTO dto) {
        return ResponseEntity.ok(incomeService.addIncome(dto));
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Income>> addIncomes(@RequestBody List<IncomeDTO> dtos) {
        return ResponseEntity.ok(incomeService.addIncomes(dtos));
    }

    @GetMapping
    public ResponseEntity<List<Income>> getAllIncome() {
        return ResponseEntity.ok(incomeService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
        return ResponseEntity.noContent().build();
    }
}
