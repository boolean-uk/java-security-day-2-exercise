package com.booleanuk.library.repository;

import com.booleanuk.library.model.Loan;
import com.booleanuk.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    public List<Loan> findByUser(User user);
}
