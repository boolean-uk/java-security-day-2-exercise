package com.booleanuk.library.controllers;

import com.booleanuk.library.models.Game;
import com.booleanuk.library.repository.GameRepository;
import com.booleanuk.library.payload.response.ErrorResponse;
import com.booleanuk.library.payload.response.GameListResponse;
import com.booleanuk.library.payload.response.GameResponse;
import com.booleanuk.library.payload.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("games")
public class GameController {
    @Autowired
    GameRepository gameRepository;

    @GetMapping
    public ResponseEntity<GameListResponse> getAllBooks() {
        GameListResponse gameListResponse = new GameListResponse();
        gameListResponse.set(this.gameRepository.findAll());
        return ResponseEntity.ok(gameListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getBookById(@PathVariable int id) {
        Game game = this.gameRepository.findById(id).orElse(null);
        if (game == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        GameResponse bookResponse = new GameResponse();
        bookResponse.set(game);
        return ResponseEntity.ok(bookResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> updateBook(@RequestBody Game game) {
        Game game1;
        try {
            game1 = this.gameRepository.save(game);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not create game");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        GameResponse bookResponse = new GameResponse();
        bookResponse.set(game1);
        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateBook(@PathVariable int id, @RequestBody Game game) {
        Game game1 = this.gameRepository.findById(id).orElse(null);
        if (game1 == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No game with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        try {
            game1.setTitle(game.getTitle());
            game1.setGenre(game.getGenre());
            game1.setStudio(game.getStudio());
            game1.setAgeRating(game.getAgeRating());
            game1.setIsBorrowed(game.getIsBorrowed());
            this.gameRepository.save(game1);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not update game, please check all fields are correct.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        GameResponse bookResponse = new GameResponse();
        bookResponse.set(game1);
        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteAuthor(@PathVariable int id) {
        Game game1 = this.gameRepository.findById(id).orElse(null);
        if (game1 == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No game with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.gameRepository.delete(game1);
        GameResponse bookResponse = new GameResponse();
        bookResponse.set(game1);
        return ResponseEntity.ok(bookResponse);
    }
}
