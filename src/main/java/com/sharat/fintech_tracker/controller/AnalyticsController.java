package com.sharat.fintech_tracker.controller;

import com.sharat.fintech_tracker.dto.MonthlyHistoryDTO;
import com.sharat.fintech_tracker.model.User;
import com.sharat.fintech_tracker.service.AnalyticsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/expenses/total")
    public double getTotalExpenses(@RequestParam Long userId,
                                   @RequestParam Month month,
                                   @RequestParam int year) {
        return analyticsService.getTotalExpenses(userId, month, year);
    }

    @GetMapping("/income/total")
    public double getTotalIncome(@RequestParam Long userId,
                                 @RequestParam Month month,
                                 @RequestParam int year) {
        return analyticsService.getTotalIncome(userId, month, year);
    }

    @GetMapping("/expenses/by-category")
    public Map<String, Double> getExpensesByCategory(@RequestParam Long userId,
                                                     @RequestParam Month month,
                                                     @RequestParam int year) {
        return analyticsService.getExpensesByCategory(userId, month, year);
    }

    @GetMapping("/budget/remaining")
    public Map<String, Double> getRemainingBudgetByCategory(@RequestParam Long userId,
                                                            @RequestParam Month month,
                                                            @RequestParam int year) {
        return analyticsService.getRemainingBudgetByCategory(userId, month, year);
    }
    @GetMapping("/monthly-history")
    public MonthlyHistoryDTO getMonthlyHistory(@AuthenticationPrincipal User user) {
        return analyticsService.getMonthlyHistory(user);
    }

}
