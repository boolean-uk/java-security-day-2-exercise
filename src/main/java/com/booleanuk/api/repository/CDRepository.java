package com.booleanuk.api.repository;

import com.booleanuk.api.models.CD;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CDRepository extends JpaRepository<CD, Integer> {
}
