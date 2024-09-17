package com.booleanuk.api.repository;

import com.booleanuk.api.model.Item;
import com.booleanuk.api.model.Loan;
import com.booleanuk.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    List<Loan> findByUserAndItem(User user, Item item);

    List<Loan> findByReturnedAndItem(boolean returned, Item item);

    Loan findByIdAndUserAndItem(int id, User user, Item item);
}
