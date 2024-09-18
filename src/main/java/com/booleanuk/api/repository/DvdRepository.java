package com.booleanuk.api.repository;

import com.booleanuk.api.models.Dvd;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DvdRepository extends JpaRepository<Dvd, Integer> {
}

