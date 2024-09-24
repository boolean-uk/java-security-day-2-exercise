package com.booleanuk.api.control;


import com.booleanuk.api.model.BoardGame;
import com.booleanuk.api.model.ResponseObject;
import com.booleanuk.api.view.BoardGameRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("boardgames")
public class BoardGameController {

	private final BoardGameRepository boardGameRepo;

	private HashMap<String, String> errorMessage;

	public BoardGameController(BoardGameRepository boardGameRepository) {
		this.boardGameRepo = boardGameRepository;
		this.errorMessage = new HashMap<>();
		errorMessage.put("message", "Failed");
	}

	@GetMapping
	public ResponseEntity<ResponseObject<List<BoardGame>>> getAll() {
		return new ResponseEntity<>(new ResponseObject<>("Success", boardGameRepo.findAll()), HttpStatus.OK);
	}

	public void checkIfValidBoardGame(BoardGame boardGame) {
		try {
			if (boardGame.getName() == null || boardGame.getName().isEmpty()) {
				throw new Exception("Invalid board game name");
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request body: " + e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<ResponseObject<?>> postOne(@RequestBody BoardGame boardGame) {
		try {
			checkIfValidBoardGame(boardGame);
		} catch (ResponseStatusException e) {
			return boardGameInvalidRequest();
		}

		return new ResponseEntity<>(new ResponseObject<>("Success", boardGameRepo.save(boardGame)), HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	public ResponseEntity<ResponseObject<?>> putOne(@PathVariable int id, @RequestBody BoardGame boardGame) {
		BoardGame boardGameToUpdate;
		try {
			boardGameToUpdate = boardGameRepo.findById(id)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

			checkIfValidBoardGame(boardGame);
		} catch (ResponseStatusException e) {
			return boardGameNotFound();
		}

		// Update fields
		boardGameToUpdate.setName(boardGame.getName());

		return new ResponseEntity<>(new ResponseObject<>("Success", boardGameRepo.save(boardGameToUpdate)), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<ResponseObject<?>> deleteBoardGame(@PathVariable int id) {
		BoardGame delBoardGame;
		try {
			delBoardGame = boardGameRepo.findById(id)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		} catch (ResponseStatusException e) {
			return boardGameNotFound();
		}

		boardGameRepo.delete(delBoardGame);
		return new ResponseEntity<>(new ResponseObject<>("Success", delBoardGame), HttpStatus.OK);
	}

	public ResponseEntity<ResponseObject<?>> boardGameNotFound() {
		return new ResponseEntity<>(new ResponseObject<>("Failed: Board game not found"), HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<ResponseObject<?>> boardGameInvalidRequest() {
		return new ResponseEntity<>(new ResponseObject<>("Failed: Invalid request body"), HttpStatus.BAD_REQUEST);
	}
}
