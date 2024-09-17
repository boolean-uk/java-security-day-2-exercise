package com.booleanuk.api.service;

import com.booleanuk.api.models.Item;
import com.booleanuk.api.models.Loan;
import com.booleanuk.api.models.User;
import com.booleanuk.api.repository.ItemRepository;
import com.booleanuk.api.repository.LoanRepository;
import com.booleanuk.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;


    public Loan borrowItem(Loan loan) {
        User user = userRepository.findById(loan.getUser().getId()).orElse(null);
        Item item = itemRepository.findById(loan.getItem().getId()).orElse(null);
        if (user == null || item == null) {
            throw new RuntimeException("User not found");
        }

        loan.setUser(user);
        loan.setItem(item);
        loan.setBorrowedAt(new Date());
        return loanRepository.save(loan);
    }

    public Loan returnItem(Integer loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow();
        loan.setReturnedAt(new Date());
        return loanRepository.save(loan);
    }

    public List<Loan> getUserLoans(Integer userId) {
        return loanRepository.findByUserId(userId);
    }

    public List<Loan> getItemLoans(Integer itemId) {
        return loanRepository.findByItemId(itemId);
    }
}