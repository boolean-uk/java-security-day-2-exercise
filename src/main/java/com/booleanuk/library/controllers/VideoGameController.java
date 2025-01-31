package com.booleanuk.library.controllers;

import com.booleanuk.library.models.VideoGame;
import com.booleanuk.library.payload.response.*;
import com.booleanuk.library.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("video_games")
public class VideoGameController {
    @Autowired
    private VideoGameRepository videoGameRepository;

    @GetMapping
    public ResponseEntity<VideoGameListResponse> getAllVideoGames() {
        VideoGameListResponse videoGameListResponse = new VideoGameListResponse();
        videoGameListResponse.set(this.videoGameRepository.findAll());
        return ResponseEntity.ok(videoGameListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createVideoGame(@RequestBody VideoGame videoGame) {
        VideoGameResponse response = new VideoGameResponse();
        try {
            response.set(this.videoGameRepository.save(videoGame));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getVideoGameById(@PathVariable int id) {
        VideoGame videoGame = this.videoGameRepository.findById(id).orElse(null);
        if (videoGame == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        VideoGameResponse response = new VideoGameResponse();
        response.set(videoGame);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateVideoGame(@PathVariable int id, @RequestBody VideoGame videoGame) {
        VideoGame videoGameToUpdate = this.videoGameRepository.findById(id).orElse(null);
        if (videoGameToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        videoGameToUpdate.setTitle(videoGame.getTitle());
        videoGameToUpdate.setYear(videoGame.getYear());
        videoGameToUpdate.setGenre(videoGame.getGenre());

        try {
            videoGameToUpdate = this.videoGameRepository.save(videoGameToUpdate);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        VideoGameResponse response = new VideoGameResponse();
        response.set(videoGameToUpdate);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteVideoGame(@PathVariable int id) {
        VideoGame videoGameToDelete = this.videoGameRepository.findById(id).orElse(null);
        if (videoGameToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.videoGameRepository.delete(videoGameToDelete);
        VideoGameResponse response = new VideoGameResponse();
        response.set(videoGameToDelete);
        return ResponseEntity.ok(response);
    }
}
