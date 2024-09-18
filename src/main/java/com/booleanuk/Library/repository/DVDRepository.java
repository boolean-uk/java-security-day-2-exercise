package com.booleanuk.Library.repository;

import com.booleanuk.Library.models.DVD;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DVDRepository extends JpaRepository<DVD, Integer> {
}
