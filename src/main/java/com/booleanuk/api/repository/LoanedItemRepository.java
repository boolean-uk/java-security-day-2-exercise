package com.booleanuk.api.repository;

import com.booleanuk.api.models.LoanedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface LoanedItemRepository extends JpaRepository<LoanedItem, Integer> {
    List<LoanedItem> findByUserId(int userId);
}