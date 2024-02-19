package com.booleanuk.api.controller;

import com.booleanuk.api.model.VideoGame;
import com.booleanuk.api.repository.UserRepository;
import com.booleanuk.api.repository.VideoGameRepository;
import com.booleanuk.api.payload.response.ErrorResponse;
import com.booleanuk.api.payload.response.Response;
import com.booleanuk.api.payload.response.VideoGameListResponse;
import com.booleanuk.api.payload.response.VideoGameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("videogames")
public class VideoGameController {
    @Autowired
    private VideoGameRepository repository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<VideoGameListResponse> getAllVideoGames() {
        // Success response with list of video games, can be empty
        VideoGameListResponse videoGameListResponse = new VideoGameListResponse();
        videoGameListResponse.set(this.repository.findAll());
        return ResponseEntity.ok(videoGameListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getVideoGameById(@PathVariable int id) {
        VideoGame videoGame = this.repository.findById(id).orElse(null);
        // 404 Not found
        if (videoGame == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Video game not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        // Success response of video game with specific ID
        VideoGameResponse videoGameResponse = new VideoGameResponse();
        videoGameResponse.set(videoGame);
        return ResponseEntity.ok(videoGameResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createVideoGame(@RequestBody VideoGame videoGame) {
        // 400 Bad request
        if (videoGame.getTitle() == null || videoGame.getGameStudio() == null || videoGame.getAgeRating() == null ||
                videoGame.getNumberOfPlayers() == 0 || videoGame.getGenre() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request, please check all fields are correct");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        // Success response of created video game
        VideoGameResponse videoGameResponse = new VideoGameResponse();
        videoGameResponse.set(this.repository.save(videoGame));
        return new ResponseEntity<>(videoGameResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateVideoGame(@PathVariable int id, @RequestBody VideoGame videoGame) {
        VideoGame videoGameToUpdate = this.repository.findById(id).orElse(null);
        // 404 Not found
        if (videoGameToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Video game not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        // 400 Bad request
        if (videoGame.getTitle() == null || videoGame.getGameStudio() == null || videoGame.getAgeRating() == null ||
                videoGame.getNumberOfPlayers() == 0 || videoGame.getGenre() == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request, please check all fields are correct");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        // Update fields
        videoGameToUpdate.setTitle(videoGame.getTitle());
        videoGameToUpdate.setGameStudio(videoGame.getGameStudio());
        videoGameToUpdate.setAgeRating(videoGame.getAgeRating());
        videoGameToUpdate.setNumberOfPlayers(videoGame.getNumberOfPlayers());
        videoGameToUpdate.setGenre(videoGame.getGenre());
        // Success response of updated video game
        VideoGameResponse videoGameResponse = new VideoGameResponse();
        videoGameResponse.set(this.repository.save(videoGameToUpdate));
        return new ResponseEntity<>(videoGameResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteVideoGame(@PathVariable int id) {
        VideoGame videoGameToDelete = this.repository.findById(id).orElse(null);
        // 404 Not found
        if (videoGameToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Video game not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        // Deletes video game with specified ID
        this.repository.delete(videoGameToDelete);
        // Success response of deleted video game
        VideoGameResponse videoGameResponse = new VideoGameResponse();
        videoGameResponse.set(videoGameToDelete);
        return ResponseEntity.ok(videoGameResponse);
    }

}
