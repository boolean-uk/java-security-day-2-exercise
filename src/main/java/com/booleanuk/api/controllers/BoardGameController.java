package com.booleanuk.api.controllers;

import com.booleanuk.api.models.BoardGame;
import com.booleanuk.api.repository.BoardGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/boardgames")
public class BoardGameController {

    @Autowired
    private BoardGameRepository boardGameRepository;

    @PostMapping
    public ResponseEntity<BoardGame> createBoardGame(@RequestBody BoardGame boardGame) {
        return new ResponseEntity<>(this.boardGameRepository.save(boardGame), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BoardGame>> getAllBoardGames() {
        return ResponseEntity.ok(this.boardGameRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardGame> getOneBoardGame(@PathVariable int id) {
        BoardGame boardGame = this.boardGameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board Game with ID " + id + " not found."));
        return ResponseEntity.ok(boardGame);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardGame> updateBoardGame(@PathVariable int id, @RequestBody BoardGame boardGameDetails) {
        BoardGame boardGameToUpdate = this.boardGameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board Game with ID " + id + " not found."));

        boardGameToUpdate.setTitle(boardGameDetails.getTitle());
        boardGameToUpdate.setPublisher(boardGameDetails.getPublisher());
        boardGameToUpdate.setGenre(boardGameDetails.getGenre());
        boardGameToUpdate.setMinPlayers(boardGameDetails.getMinPlayers());
        boardGameToUpdate.setMaxPlayers(boardGameDetails.getMaxPlayers());
        boardGameToUpdate.setPlayTimeInMinutes(boardGameDetails.getPlayTimeInMinutes());

        return new ResponseEntity<>(this.boardGameRepository.save(boardGameToUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoardGame(@PathVariable int id) {
        BoardGame boardGameToDelete = this.boardGameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board Game with ID " + id + " not found."));
        this.boardGameRepository.delete(boardGameToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
