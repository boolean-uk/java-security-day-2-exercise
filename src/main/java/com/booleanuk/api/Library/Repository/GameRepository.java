package com.booleanuk.api.Library.Repository;

import com.booleanuk.api.Library.Model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Integer> {

}
