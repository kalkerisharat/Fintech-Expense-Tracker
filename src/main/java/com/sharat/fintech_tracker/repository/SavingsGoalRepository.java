package com.sharat.fintech_tracker.repository;

import com.sharat.fintech_tracker.model.SavingsGoal;
import com.sharat.fintech_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SavingsGoalRepository extends JpaRepository<SavingsGoal, Long> {
    List<SavingsGoal> findByUser(User user);
}
