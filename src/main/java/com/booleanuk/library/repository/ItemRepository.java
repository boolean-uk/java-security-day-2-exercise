package com.booleanuk.library.repository;

import com.booleanuk.library.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
