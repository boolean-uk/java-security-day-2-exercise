package com.booleanuk.Library.repository;

import com.booleanuk.Library.models.VideoGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoGameRepository extends JpaRepository<VideoGame, Integer> {
}
