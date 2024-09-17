package com.booleanuk.library.repository;

import com.booleanuk.library.models.Borrowing;

import com.booleanuk.library.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowingRepository extends JpaRepository<Borrowing,Integer> {

    List<Borrowing> findByUser(User user);

}
