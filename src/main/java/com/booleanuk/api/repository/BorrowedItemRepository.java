package com.booleanuk.api.repository;

import com.booleanuk.api.dto.BorrowedItemDto;
import com.booleanuk.api.models.BorrowedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BorrowedItemRepository extends JpaRepository<BorrowedItem, Integer> {
    @Query("SELECT new com.booleanuk.api.dto.BorrowedItemDto(b.id, i.name, u.username, b.borrowDate, b.returnDate) " +
            "FROM BorrowedItem b " +
            "JOIN b.item i " +
            "JOIN b.user u " +
            "WHERE i.id = :itemId")
    List<BorrowedItemDto> findBorrowedItemsByItemId(int itemId);

    @Query("SELECT new com.booleanuk.api.dto.BorrowedItemDto(b.id, i.name, u.username, b.borrowDate, b.returnDate) " +
            "FROM BorrowedItem b " +
            "JOIN b.item i " +
            "JOIN b.user u " +
            "WHERE u.id = :userId AND  b.returnDate IS NULL")
    List<BorrowedItemDto> findCurrentlyBorrowedItemsByUserId(int userId);

    @Query("SELECT new com.booleanuk.api.dto.BorrowedItemDto(b.id, i.name, u.username, b.borrowDate, b.returnDate) " +
            "FROM BorrowedItem b " +
            "JOIN b.item i " +
            "JOIN b.user u " +
            "WHERE u.id = :userId AND b.returnDate IS NOT NULL")
    List<BorrowedItemDto> findPreviouslyBorrowedItemsByUserId(int userId);

    List<BorrowedItem> findByUserIdAndItemId(int userId, int itemId);
}
