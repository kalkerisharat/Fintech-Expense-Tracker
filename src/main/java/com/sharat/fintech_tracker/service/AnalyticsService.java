package com.sharat.fintech_tracker.service;

import com.sharat.fintech_tracker.dto.MonthlyHistoryDTO;
import com.sharat.fintech_tracker.model.User;

import java.time.Month;
import java.util.Map;

public interface AnalyticsService {
    double getTotalExpenses(Long userId, Month month, int year);
    double getTotalIncome(Long userId, Month month, int year);
    Map<String, Double> getExpensesByCategory(Long userId, Month month, int year);
    Map<String, Double> getRemainingBudgetByCategory(Long userId, Month month, int year);
    MonthlyHistoryDTO getMonthlyHistory(User user);
}
