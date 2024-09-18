package com.booleanuk.library.repository;

import com.booleanuk.library.model.DVD;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DVDRepository extends JpaRepository<DVD, Integer> {
}
