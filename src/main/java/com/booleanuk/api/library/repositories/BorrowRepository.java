package com.booleanuk.api.library.repositories;

import com.booleanuk.api.library.models.Borrow;
import com.booleanuk.api.library.models.Item;
import com.booleanuk.api.library.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BorrowRepository extends JpaRepository<Borrow, Integer> {
    List<Borrow> findItemByUserIdAndReturned(int userId, LocalDate localDate);
    List<Borrow> findItemByUserId(int userId);
    List<Borrow> findUserByItemId(int itemId);
}
