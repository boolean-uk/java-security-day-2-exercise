package com.booleanuk.library.controllers;

import com.booleanuk.library.models.BoardGame;
import com.booleanuk.library.payload.response.*;
import com.booleanuk.library.repository.BoardGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("board_games")
public class BoardGameController {
    @Autowired
    private BoardGameRepository boardGameRepository;

    @GetMapping
    public ResponseEntity<BoardGameListResponse> getAllBoardGames() {
        BoardGameListResponse boardGameListResponse = new BoardGameListResponse();
        boardGameListResponse.set(this.boardGameRepository.findAll());
        return ResponseEntity.ok(boardGameListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createBoardGame(@RequestBody BoardGame boardGame) {
        BoardGameResponse response = new BoardGameResponse();
        try {
            response.set(this.boardGameRepository.save(boardGame));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getBoardGameById(@PathVariable int id) {
        BoardGame boardGame = this.boardGameRepository.findById(id).orElse(null);
        if (boardGame == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        BoardGameResponse response = new BoardGameResponse();
        response.set(boardGame);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateBoardGame(@PathVariable int id, @RequestBody BoardGame boardGame) {
        BoardGame boardGameToUpdate = this.boardGameRepository.findById(id).orElse(null);
        if (boardGameToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        boardGameToUpdate.setTitle(boardGame.getTitle());
        boardGameToUpdate.setYear(boardGame.getYear());
        boardGameToUpdate.setGenre(boardGame.getGenre());

        try {
            boardGameToUpdate = this.boardGameRepository.save(boardGameToUpdate);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        BoardGameResponse response = new BoardGameResponse();
        response.set(boardGameToUpdate);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteBoardGame(@PathVariable int id) {
        BoardGame boardGameToDelete = this.boardGameRepository.findById(id).orElse(null);
        if (boardGameToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.boardGameRepository.delete(boardGameToDelete);
        BoardGameResponse response = new BoardGameResponse();
        response.set(boardGameToDelete);
        return ResponseEntity.ok(response);
    }
}
