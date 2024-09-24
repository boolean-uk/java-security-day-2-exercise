package com.booleanuk.api.control;

import com.booleanuk.api.model.VideoGame;
import com.booleanuk.api.model.ResponseObject;
import com.booleanuk.api.view.VideoGameRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("videogames")
public class VideoGameController {

	private final VideoGameRepository videoGameRepo;

	private HashMap<String, String> errorMessage;

	public VideoGameController(VideoGameRepository videoGameRepository) {
		this.videoGameRepo = videoGameRepository;
		this.errorMessage = new HashMap<>();
		errorMessage.put("message", "Failed");
	}

	@GetMapping
	public ResponseEntity<ResponseObject<List<VideoGame>>> getAll() {
		return new ResponseEntity<>(new ResponseObject<>("Success", videoGameRepo.findAll()), HttpStatus.OK);
	}

	public void checkIfValidVideoGame(VideoGame videoGame) {
		try {
			if (videoGame.getName() == null || videoGame.getName().isEmpty()) {
				throw new Exception("Invalid video game name");
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request body: " + e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<ResponseObject<?>> postOne(@RequestBody VideoGame videoGame) {
		try {
			checkIfValidVideoGame(videoGame);
		} catch (ResponseStatusException e) {
			return videoGameInvalidRequest();
		}

		return new ResponseEntity<>(new ResponseObject<>("Success", videoGameRepo.save(videoGame)), HttpStatus.CREATED);
	}

	@PutMapping("{id}")
	public ResponseEntity<ResponseObject<?>> putOne(@PathVariable int id, @RequestBody VideoGame videoGame) {
		VideoGame videoGameToUpdate;
		try {
			videoGameToUpdate = videoGameRepo.findById(id)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

			checkIfValidVideoGame(videoGame);
		} catch (ResponseStatusException e) {
			return videoGameNotFound();
		}

		videoGameToUpdate.setName(videoGame.getName());

		return new ResponseEntity<>(new ResponseObject<>("Success", videoGameRepo.save(videoGameToUpdate)), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<ResponseObject<?>> deleteVideoGame(@PathVariable int id) {
		VideoGame delVideoGame;
		try {
			delVideoGame = videoGameRepo.findById(id)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
		} catch (ResponseStatusException e) {
			return videoGameNotFound();
		}

		videoGameRepo.delete(delVideoGame);
		return new ResponseEntity<>(new ResponseObject<>("Success", delVideoGame), HttpStatus.OK);
	}

	public ResponseEntity<ResponseObject<?>> videoGameNotFound() {
		return new ResponseEntity<>(new ResponseObject<>("Failed: Video game not found"), HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<ResponseObject<?>> videoGameInvalidRequest() {
		return new ResponseEntity<>(new ResponseObject<>("Failed: Invalid request body"), HttpStatus.BAD_REQUEST);
	}
}
