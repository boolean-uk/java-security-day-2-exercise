package com.booleanuk.api.library.repository;

import com.booleanuk.api.library.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
}
