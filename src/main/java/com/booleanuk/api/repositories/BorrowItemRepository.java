package com.booleanuk.api.repositories;

import com.booleanuk.api.models.BorrowItem;
import com.booleanuk.api.models.Item;
import com.booleanuk.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BorrowItemRepository extends JpaRepository<BorrowItem, Integer> {
    Optional<BorrowItem> findByUserAndItem(User user, Item item);
    Optional<List<BorrowItem>> findAllByUser(User customer);
    Optional<List<BorrowItem>> findAllByItem(Item item);
    boolean existsByItemAndIsActiveTrue(Item item);
}
