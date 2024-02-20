package com.booleanuk.library.repository;

import com.booleanuk.library.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Query("SELECT i FROM Item i WHERE i.userBorrowing = ?1")
    List<Item> findByUserBorrowing(int userBorrowing);
}
