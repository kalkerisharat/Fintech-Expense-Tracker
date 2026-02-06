package com.sharat.fintech_tracker.service;

import com.sharat.fintech_tracker.dto.IncomeDTO;
import com.sharat.fintech_tracker.model.*;
import com.sharat.fintech_tracker.repository.RecurringTransactionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecurringTransactionService {

    private final RecurringTransactionRepository repository;
    private final ExpenseService expenseService;
    private final IncomeService incomeService;

    public RecurringTransactionService(RecurringTransactionRepository repository,
                                       ExpenseService expenseService,
                                       IncomeService incomeService) {
        this.repository = repository;
        this.expenseService = expenseService;
        this.incomeService = incomeService;
    }

    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    @Transactional
    public void processRecurringTransactions() {
        LocalDate today = LocalDate.now();
        // Fetch transactions due today or earlier
        List<RecurringTransaction> dueTransactions = repository.findByNextDueDateBefore(today.plusDays(1));

        for (RecurringTransaction tx : dueTransactions) {
            if (tx.getType() == RecurringTransaction.TransactionType.EXPENSE) {
                Expense expense = new Expense();
                expense.setAmount(tx.getAmount());
                expense.setCategory(ExpenseCategory.valueOf(tx.getCategory()));
                expense.setDescription("Recurring: " + tx.getFrequency());
                expense.setDate(today);
                expense.setUser(tx.getUser());

                expenseService.addExpense(expense, tx.getUser());
            } else {
                IncomeDTO dto = new IncomeDTO();
                dto.setAmount(tx.getAmount());
                dto.setCategory(IncomeCategory.valueOf(tx.getCategory()));
                dto.setDescription("Recurring: " + tx.getFrequency());

                // FIX: Pass LocalDate directly to the DTO
                dto.setDate(today);

                incomeService.addIncome(dto, tx.getUser());
            }

            updateNextDueDate(tx);
        }
    }

    private void updateNextDueDate(RecurringTransaction tx) {
        // Ensure we calculate the next date based on the current NextDueDate to avoid drift
        switch (tx.getFrequency()) {
            case DAILY -> tx.setNextDueDate(tx.getNextDueDate().plusDays(1));
            case WEEKLY -> tx.setNextDueDate(tx.getNextDueDate().plusWeeks(1));
            case MONTHLY -> tx.setNextDueDate(tx.getNextDueDate().plusMonths(1));
        }
        repository.save(tx);
    }
}