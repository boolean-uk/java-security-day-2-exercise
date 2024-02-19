package com.booleanuk.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.booleanuk.api.models.Item;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
