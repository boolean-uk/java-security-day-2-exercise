package com.booleanuk.library.controllers;

import com.booleanuk.library.models.VideoGame;
import com.booleanuk.library.payload.response.ErrorResponse;
import com.booleanuk.library.payload.response.Response;
import com.booleanuk.library.payload.response.VideoGameResponse;
import com.booleanuk.library.repository.RoleRepository;
import com.booleanuk.library.repository.UserRepository;
import com.booleanuk.library.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/videogames")
public class VideoGameController {
	@Autowired
	private VideoGameRepository videoGameRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;


	@GetMapping
	public ResponseEntity<List<VideoGame>> getAllVideoGames() {
		List<VideoGame> videoGames = videoGameRepository.findAll();
		return new ResponseEntity<>(videoGames, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response<?>> getVideoGameById(@PathVariable int id) {
		VideoGame videoGame = videoGameRepository.findById(id).orElse(null);
		if (videoGame != null) {
			VideoGameResponse videoGameResponse = new VideoGameResponse();
			videoGameResponse.set(videoGame);
			return ResponseEntity.ok(videoGameResponse);
		} else {
			ErrorResponse error = new ErrorResponse();
			error.set("not found");
			return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping
	public ResponseEntity<Response<?>> addVideoGame(@RequestBody VideoGame videoGame) {

		VideoGame newVideoGame = videoGameRepository.save(videoGame);
		VideoGameResponse videoGameResponse = new VideoGameResponse();
		videoGameResponse.set(newVideoGame);
		return new ResponseEntity<>(videoGameResponse, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Response<?>> updateVideoGame(@PathVariable int id, @RequestBody VideoGame videoGame) {

		VideoGame updatedVideoGame = videoGameRepository.findById(id).orElse(null);
		if (updatedVideoGame != null) {

			if (videoGame.getTitle() != null) {
				updatedVideoGame.setTitle(videoGame.getTitle());
			}
			if (videoGame.getGameStudio() != null) {
				updatedVideoGame.setGameStudio(videoGame.getGameStudio());
			}
			if (videoGame.getGenre() != null) {
				updatedVideoGame.setGenre(videoGame.getGenre());
			}
			if (videoGame.getAgeRating() != null) {
				updatedVideoGame.setAgeRating(videoGame.getAgeRating());
			}
			if (videoGame.getNumberOfPlayers() != 0) {
				updatedVideoGame.setNumberOfPlayers(videoGame.getNumberOfPlayers());
			}
			videoGameRepository.save(updatedVideoGame);
			VideoGameResponse videoGameResponse = new VideoGameResponse();
			videoGameResponse.set(videoGame);
			return ResponseEntity.ok(videoGameResponse);
		} else {
			ErrorResponse error = new ErrorResponse();
			error.set("not found");
			return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response<?>> deleteVideoGame(@PathVariable int id) {

		VideoGame videoGame = videoGameRepository.findById(id).orElse(null);
		if (videoGame == null) {
			ErrorResponse error = new ErrorResponse();
			error.set("not found");
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
		this.videoGameRepository.delete(videoGame);
		VideoGameResponse videoGameResponse = new VideoGameResponse();
		videoGameResponse.set(videoGame);
		return ResponseEntity.ok(videoGameResponse);

	}

}
