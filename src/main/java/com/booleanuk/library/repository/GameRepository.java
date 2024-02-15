package com.booleanuk.library.repository;

import com.booleanuk.library.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {
}
