package com.booleanuk.api.controllers;

import com.booleanuk.api.models.BoardGame;
import com.booleanuk.api.payload.response.BoardGameListResponse;
import com.booleanuk.api.payload.response.BoardGameResponse;
import com.booleanuk.api.payload.response.ErrorResponse;
import com.booleanuk.api.payload.response.Response;
import com.booleanuk.api.repository.BoardGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("boardGames")
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
        BoardGameResponse boardGameResponse = new BoardGameResponse();
        try {
            boardGameResponse.set(this.boardGameRepository.save(boardGame));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(boardGameResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getBoardGameById(@PathVariable int id) {
        BoardGame boardGame = this.boardGameRepository.findById(id).orElse(null);
        if (boardGame == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        BoardGameResponse boardGameResponse = new BoardGameResponse();
        boardGameResponse.set(boardGame);
        return ResponseEntity.ok(boardGameResponse);
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
        boardGameToUpdate.setCreator(boardGame.getCreator());
        boardGameToUpdate.setPublisher(boardGame.getPublisher());
        boardGameToUpdate.setYear(boardGame.getYear());
        boardGameToUpdate.setGenre(boardGame.getGenre());

        try {
            boardGameToUpdate = this.boardGameRepository.save(boardGameToUpdate);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        BoardGameResponse boardGameResponse = new BoardGameResponse();
        boardGameResponse.set(boardGameToUpdate);
        return new ResponseEntity<>(boardGameResponse, HttpStatus.CREATED);
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
        BoardGameResponse boardGameResponse = new BoardGameResponse();
        boardGameResponse.set(boardGameToDelete);
        return ResponseEntity.ok(boardGameResponse);
    }
}
