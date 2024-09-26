package com.booleanuk.api.repository;

import com.booleanuk.api.models.ItemLoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemLoanRepository extends JpaRepository<ItemLoan, Integer> {
}
