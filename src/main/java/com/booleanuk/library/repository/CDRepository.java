package com.booleanuk.library.repository;

import com.booleanuk.library.model.CD;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CDRepository extends JpaRepository<CD, Integer> {
}
