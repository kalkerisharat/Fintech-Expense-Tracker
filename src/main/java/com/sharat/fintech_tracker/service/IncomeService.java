package com.sharat.fintech_tracker.service;

import com.sharat.fintech_tracker.dto.IncomeDTO;
import com.sharat.fintech_tracker.model.Income;
import com.sharat.fintech_tracker.model.User;
import com.sharat.fintech_tracker.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private UserService userService;

    public Income addIncome(IncomeDTO dto) {
        User user = userService.getLoggedInUser();
        Income income = new Income();
        income.setSource(dto.getSource());
        income.setAmount(dto.getAmount());
        income.setDate(dto.getDate());
        income.setUser(user);
        return incomeRepository.save(income);
    }

    public List<Income> addIncomes(List<IncomeDTO> dtos) {
        User user = userService.getLoggedInUser();
        List<Income> incomes = new ArrayList<>();
        for (IncomeDTO dto : dtos) {
            Income income = new Income();
            income.setSource(dto.getSource());
            income.setAmount(dto.getAmount());
            income.setDate(dto.getDate());
            income.setUser(user);
            incomes.add(incomeRepository.save(income));
        }
        return incomes;
    }

    public List<Income> getAll() {
        return incomeRepository.findByUser(userService.getLoggedInUser());
    }

    public void deleteIncome(Long id) {
        incomeRepository.deleteById(id);
    }
}
