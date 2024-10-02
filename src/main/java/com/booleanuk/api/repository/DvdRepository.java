package com.booleanuk.api.repository;

import com.booleanuk.api.models.DVD;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DvdRepository extends JpaRepository<DVD, Integer> {
}
