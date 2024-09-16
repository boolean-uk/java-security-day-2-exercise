package com.booleanuk.api.repositories;

import com.booleanuk.api.models.Lend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LendRepository extends JpaRepository<Lend, Integer> {
}
