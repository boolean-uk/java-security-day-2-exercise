package com.booleanuk.api.repository;

import com.booleanuk.api.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    List<Loan> findAllByVideoGameId(int id);
    List<Loan> findByReturnedFalse();
}
