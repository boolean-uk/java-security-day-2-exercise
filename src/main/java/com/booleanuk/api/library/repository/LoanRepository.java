package com.booleanuk.api.library.repository;

import com.booleanuk.api.library.models.Item;
import com.booleanuk.api.library.models.Loan;
import com.booleanuk.api.library.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    List<Loan> findByUser(User user);
    List<Loan> findByItem(Item item);
}
