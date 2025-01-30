package com.booleanuk.api.library.repository;

import com.booleanuk.api.library.models.Item;
import com.booleanuk.api.library.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    //Item findByLoan(Loan loan);
}
