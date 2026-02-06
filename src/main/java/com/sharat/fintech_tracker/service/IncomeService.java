package com.sharat.fintech_tracker.service;

import com.sharat.fintech_tracker.dto.IncomeDTO;
import com.sharat.fintech_tracker.model.Income;
import com.sharat.fintech_tracker.model.User;
import com.sharat.fintech_tracker.repository.IncomeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final UserService userService;

    // Using Constructor Injection instead of @Autowired (Best Practice)
    public IncomeService(IncomeRepository incomeRepository, UserService userService) {
        this.incomeRepository = incomeRepository;
        this.userService = userService;
    }

    @Transactional
    public Income addIncome(IncomeDTO dto, User user) {
        Income income = new Income();
        income.setCategory(dto.getCategory());
        income.setAmount(dto.getAmount());
        income.setDescription(dto.getDescription());
        income.setDate(dto.getDate()); // No more conversion error!
        income.setUser(user);
        return incomeRepository.save(income);
    }

    @Transactional
    public List<Income> addIncomes(List<IncomeDTO> dtos, User user) {
        return dtos.stream()
                .map(dto -> addIncome(dto, user))
                .collect(Collectors.toList());
    }

    public List<Income> getAll(User user) {
        return incomeRepository.findByUser(user);
    }

    @Transactional
    public void deleteIncome(Long id, User user) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income record not found"));

        // 🔐 Security: Check ownership
        if (!income.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized: Access denied to this income record.");
        }

        incomeRepository.delete(income);
    }
}