package com.booleanuk.api.view;

import com.booleanuk.api.model.BoardGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardGameRepository extends JpaRepository<BoardGame, Integer> {
}
