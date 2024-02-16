package com.booleanuk.api.repositories;

import com.booleanuk.api.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
}
