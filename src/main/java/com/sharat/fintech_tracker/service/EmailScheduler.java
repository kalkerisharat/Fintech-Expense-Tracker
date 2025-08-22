package com.sharat.fintech_tracker.service;
import com.sharat.fintech_tracker.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailScheduler {

    private final EmailService emailService;
    private final ExpenseService expenseService;

    @Autowired
    public EmailScheduler(EmailService emailService, ExpenseService expenseService) {
        this.emailService = emailService;
        this.expenseService = expenseService;
    }

    @Scheduled(cron = "0 0 8 1 * ?") // Example: Send email every 1st of the month at 8:00 AM
    public void sendMonthlyReport() throws Exception {
        List<Expense> expenses = expenseService.getAllExpenses();
        ByteArrayResource pdfResource = generatePdfReportAsResource(expenses);
        emailService.sendExpenseReportEmail("user@example.com", expenses, pdfResource);
    }

    private ByteArrayResource generatePdfReportAsResource(List<Expense> expenses) {
        return null;
    }
}
