package com.booleanuk.library.controllers;

import com.booleanuk.library.models.BoardGame;
import com.booleanuk.library.repository.BoardGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boardgames")
public class BoardGameController {

	@Autowired
	private BoardGameRepository boardGameRepository;

	@GetMapping
	public ResponseEntity<Iterable<BoardGame>> getAllBoardGames() {
		Iterable<BoardGame> boardGames = boardGameRepository.findAll();
		return ResponseEntity.ok(boardGames);
	}

	@PostMapping
	public ResponseEntity<BoardGame> createBoardGame(@RequestBody BoardGame boardGame) {
		BoardGame savedBoardGame = boardGameRepository.save(boardGame);
		return new ResponseEntity<>(savedBoardGame, HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<BoardGame> getBoardGameById(@PathVariable int id) {
		BoardGame boardGame = boardGameRepository.findById(id).orElse(null);
		if (boardGame == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(boardGame);
	}

	@PutMapping("/{id}")
	public ResponseEntity<BoardGame> updateBoardGame(@PathVariable int id, @RequestBody BoardGame updatedBoardGame) {
		BoardGame boardGame = boardGameRepository.findById(id).orElse(null);
		if (boardGame == null) {
			return ResponseEntity.notFound().build();
		}
		// Update the board game properties
		boardGame.setName(updatedBoardGame.getName());
		boardGame.setPublisher(updatedBoardGame.getPublisher());
		boardGame.setYear(updatedBoardGame.getYear());
		// Save the updated board game
		BoardGame savedBoardGame = boardGameRepository.save(boardGame);
		return ResponseEntity.ok(savedBoardGame);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBoardGame(@PathVariable int id) {
		BoardGame boardGame = boardGameRepository.findById(id).orElse(null);
		if (boardGame == null) {
			return ResponseEntity.notFound().build();
		}
		boardGameRepository.delete(boardGame);
		return ResponseEntity.noContent().build();
	}
}
