package com.sharat.fintech_tracker.service;

import com.sharat.fintech_tracker.model.Expense;
import com.sharat.fintech_tracker.model.User;
import com.sharat.fintech_tracker.repository.UserRepository; // You'll need this
import org.springframework.core.io.ByteArrayResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailScheduler {

    private final EmailService emailService;
    private final ExpenseService expenseService;
    private final UserRepository userRepository;

    public EmailScheduler(EmailService emailService, ExpenseService expenseService, UserRepository userRepository) {
        this.emailService = emailService;
        this.expenseService = expenseService;
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 0 8 1 * ?") // 1st of every month at 8:00 AM
    public void sendMonthlyReport() {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            try {
                // Now passing the specific user to get their specific expenses
                List<Expense> userExpenses = expenseService.getAllExpenses(user);

                if (!userExpenses.isEmpty()) {
                    ByteArrayResource pdfResource = generatePdfReportAsResource(userExpenses);
                    emailService.sendExpenseReportEmail(user.getEmail(), userExpenses, pdfResource);
                }
            } catch (Exception e) {
                // Log the error for this specific user and continue with others
                System.err.println("Failed to send report to " + user.getEmail() + ": " + e.getMessage());
            }
        }
    }

    private ByteArrayResource generatePdfReportAsResource(List<Expense> expenses) {
        // Implementation for PDF generation
        return null;
    }
}