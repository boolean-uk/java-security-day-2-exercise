package com.booleanuk.library.repository;

import com.booleanuk.library.model.VideoGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoGamesRepository extends JpaRepository<VideoGame, Integer> {
}
