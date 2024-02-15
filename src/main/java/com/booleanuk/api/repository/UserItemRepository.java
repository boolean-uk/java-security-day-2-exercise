package com.booleanuk.api.repository;

import com.booleanuk.api.models.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserItemRepository extends JpaRepository<UserItem, Integer> {
}
