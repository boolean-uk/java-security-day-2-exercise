package com.booleanuk.api.library.repository;

import com.booleanuk.api.library.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}