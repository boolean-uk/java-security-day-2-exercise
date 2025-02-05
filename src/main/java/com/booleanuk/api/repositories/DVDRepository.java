package com.booleanuk.api.repositories;

import com.booleanuk.api.models.DVD;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DVDRepository extends JpaRepository<DVD, Integer> {
}
