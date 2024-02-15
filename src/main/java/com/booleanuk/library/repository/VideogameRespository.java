package com.booleanuk.library.repository;

import com.booleanuk.library.models.Videogame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideogameRespository extends JpaRepository<Videogame, Integer> {
}