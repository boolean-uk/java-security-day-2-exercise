package com.booleanuk.api.repository;

import com.booleanuk.api.models.BorrowedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowedItemRepository extends JpaRepository<BorrowedItem, Integer> {
}
