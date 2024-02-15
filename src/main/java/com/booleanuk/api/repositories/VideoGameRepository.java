package com.booleanuk.api.repositories;

import com.booleanuk.api.models.VideoGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoGameRepository extends JpaRepository<VideoGame, Integer> {

}
