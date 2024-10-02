package com.booleanuk.api.repository;

import com.booleanuk.api.models.BoardGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardGame, Integer> {
}