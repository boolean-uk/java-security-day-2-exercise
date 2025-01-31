package com.booleanuk.api.repository;

import com.booleanuk.api.models.Book;
import com.booleanuk.api.models.Cd;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CdRepository extends JpaRepository<Cd, Integer> {
}