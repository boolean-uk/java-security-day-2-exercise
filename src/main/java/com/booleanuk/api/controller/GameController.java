package com.booleanuk.api.controller;

import com.booleanuk.api.model.Game;
import com.booleanuk.api.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("games")
public class GameController {
    private final GameRepository gameRepository;

    @Autowired
    public GameController(GameRepository repository) {
        this.gameRepository = repository;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Game>> getAll() {
        List<Game> games = this.gameRepository.findAll();
        return ResponseEntity.ok(games);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Game> create(@RequestBody Game body) {
        Game game = this.gameRepository.save(body);
        return new ResponseEntity<>(game, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Game> update(@PathVariable int id, @RequestBody Game body) {
        Game game = this.gameRepository.findById(id).orElse(null);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
        else {
            game.setTitle(body.getTitle());
            game.setGenre(body.getGenre());
            game.setAgeRating(body.getAgeRating());
            game.setNumberOfPlayers(body.getNumberOfPlayers());
            game.setGameStudio(body.getGameStudio());
            this.gameRepository.save(game);
        }
        return new ResponseEntity<>(game, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Game> delete(@PathVariable int id) {
        Game game = this.gameRepository.findById(id).orElse(null);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        } else {
            this.gameRepository.delete(game);
        }
        return ResponseEntity.ok(game);
    }
}