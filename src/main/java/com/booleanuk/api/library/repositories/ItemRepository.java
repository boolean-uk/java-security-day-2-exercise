package com.booleanuk.api.library.repositories;

import com.booleanuk.api.library.models.Borrow;
import com.booleanuk.api.library.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

}
