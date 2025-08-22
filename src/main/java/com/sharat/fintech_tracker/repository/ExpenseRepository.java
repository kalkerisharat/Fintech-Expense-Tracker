package com.sharat.fintech_tracker.repository;
import com.sharat.fintech_tracker.model.Expense;
import com.sharat.fintech_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
}
