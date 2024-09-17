package com.booleanuk.library.repository;

import com.booleanuk.library.models.BoardGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardGameRepository extends JpaRepository<BoardGame,Integer> {
}
