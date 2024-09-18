package com.booleanuk.Library.repository;

import com.booleanuk.Library.models.BoardGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardGameRepository extends JpaRepository<BoardGame, Integer> {
}
