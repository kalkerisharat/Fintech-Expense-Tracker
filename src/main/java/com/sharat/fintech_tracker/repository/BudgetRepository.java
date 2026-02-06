    package com.sharat.fintech_tracker.repository;
    import com.sharat.fintech_tracker.model.Budget;
    import com.sharat.fintech_tracker.model.User;
    import com.sharat.fintech_tracker.model.ExpenseCategory;
    import org.springframework.data.jpa.repository.JpaRepository;

    import java.time.Month;
    import java.util.List;
    import java.util.Optional;

    public interface BudgetRepository extends JpaRepository<Budget, Long> {

        List<Budget> findByUser(User user);

        Optional<Budget> findByUserAndCategoryAndMonthAndYear(User user, ExpenseCategory category, Month month, int year);

        List<Budget> findByUserAndMonthAndYear(User user, Month month, int year);
    }
