package com.sharat.fintech_tracker.controller;

import com.sharat.fintech_tracker.dto.MonthlyHistoryDTO;
import com.sharat.fintech_tracker.model.User;
import com.sharat.fintech_tracker.service.AnalyticsService;
import com.sharat.fintech_tracker.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.Map;
@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;
    private final UserService userService;

    public AnalyticsController(AnalyticsService analyticsService, UserService userService) {
        this.analyticsService = analyticsService;
        this.userService = userService;
    }

    @GetMapping("/expenses/total")
    public double getTotalExpenses(
            @RequestParam Month month,
            @RequestParam int year) {

        User user = userService.getLoggedInUser();
        return analyticsService.getTotalExpenses(user, month, year);
    }

    @GetMapping("/income/total")
    public double getTotalIncome(
            @RequestParam Month month,
            @RequestParam int year) {

        User user = userService.getLoggedInUser();
        return analyticsService.getTotalIncome(user, month, year);
    }

    @GetMapping("/expenses/by-category")
    public Map<String, Double> getExpensesByCategory(
            @RequestParam Month month,
            @RequestParam int year) {

        User user = userService.getLoggedInUser();
        return analyticsService.getExpensesByCategory(user, month, year);
    }

    @GetMapping("/budget/remaining")
    public Map<String, Double> getRemainingBudgetByCategory(
            @RequestParam Month month,
            @RequestParam int year) {

        User user = userService.getLoggedInUser();
        return analyticsService.getRemainingBudgetByCategory(user, month, year);
    }

    @GetMapping("/monthly-history")
    public MonthlyHistoryDTO getMonthlyHistory() {
        User user = userService.getLoggedInUser();
        return analyticsService.getMonthlyHistory(user);
    }
}
