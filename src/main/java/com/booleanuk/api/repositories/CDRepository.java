package com.booleanuk.api.repositories;

import com.booleanuk.api.models.CD;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CDRepository extends JpaRepository<CD, Integer> {
}
