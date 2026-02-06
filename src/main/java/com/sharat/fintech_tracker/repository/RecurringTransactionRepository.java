package com.sharat.fintech_tracker.repository;

import com.sharat.fintech_tracker.model.RecurringTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface RecurringTransactionRepository extends JpaRepository<RecurringTransaction, Long> {
    List<RecurringTransaction> findByNextDueDateBefore(LocalDate date);
}
