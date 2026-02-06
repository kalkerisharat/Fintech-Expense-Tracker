package com.sharat.fintech_tracker.service;

import com.sharat.fintech_tracker.dto.MonthlyHistoryDTO;
import com.sharat.fintech_tracker.model.User;

import java.time.Month;
import java.util.Map;

public interface AnalyticsService {
    double getTotalExpenses(User user, Month month, int year);

    double getTotalIncome(User user, Month month, int year);

    Map<String, Double> getExpensesByCategory(User user, Month month, int year);

    Map<String, Double> getRemainingBudgetByCategory(User user, Month month, int year);

    MonthlyHistoryDTO getMonthlyHistory(User user);
}
