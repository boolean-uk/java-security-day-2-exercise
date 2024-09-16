package com.booleanuk.api.repository;

import com.booleanuk.api.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
}
