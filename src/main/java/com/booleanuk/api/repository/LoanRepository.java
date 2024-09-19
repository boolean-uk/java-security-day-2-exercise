package com.booleanuk.api.repository;

import com.booleanuk.api.model.LoanItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanItemRepository extends JpaRepository<LoanItem, Integer> {
}
