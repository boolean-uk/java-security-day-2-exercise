package com.booleanuk.api.repository;

import com.booleanuk.api.models.BoardGame;
import com.booleanuk.api.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardGameRepository extends JpaRepository<BoardGame, Integer> {
}
