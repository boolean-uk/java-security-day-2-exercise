package com.booleanuk.api.controller;

import com.booleanuk.api.generic.GenericController;
import com.booleanuk.api.model.Game;
import com.booleanuk.api.repository.GameRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("games")
public class GameController extends GenericController<Game> {
  public GameController(GameRepository repository) {
    super(repository);
  }
}