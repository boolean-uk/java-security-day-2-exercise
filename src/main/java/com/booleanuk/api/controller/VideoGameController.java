package com.booleanuk.api.controller;

import com.booleanuk.api.model.VideoGame;
import com.booleanuk.api.repository.VideoGameRepository;
import com.booleanuk.payload.response.ErrorResponse;
import com.booleanuk.payload.response.Response;
import com.booleanuk.payload.response.VideoGameListResponse;
import com.booleanuk.payload.response.VideoGameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("video-games")
public class VideoGameController {
    @Autowired
    VideoGameRepository videoGameRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAllVideoGames()   {
        VideoGameListResponse videoGameListResponse = new VideoGameListResponse();
        videoGameListResponse.set(
                "Successfully returned a list of all video games",
                this.videoGameRepository.findAll()
        );

        return ResponseEntity.ok(videoGameListResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getOneVideoGame(@PathVariable int id)    {
        VideoGame videoGame = this.videoGameRepository.findById(id).orElse(null);
        if(videoGame == null)   {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No video game with that id were");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        VideoGameResponse videoGameResponse = new VideoGameResponse();
        videoGameResponse.set(
                "Successfully returned a video game",
                videoGame
        );
        return ResponseEntity.ok(videoGameResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createVideoGame(@RequestBody VideoGame videoGame)    {
        if(videoGame.getTitle() == null
        || videoGame.getDeveloper() == null
        || videoGame.getPublisher() == null
        || videoGame.getYear() == 0
        || videoGame.getRating() == null
        || videoGame.getGenre() == null
        || videoGame.getPlatform() == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("One or more required fields are null");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        VideoGameResponse videoGameResponse = new VideoGameResponse();
        videoGameResponse.set(
                "Successfully created video game",
                this.videoGameRepository.save(videoGame)
        );
        return new ResponseEntity<>(videoGameResponse, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateVideoGame(@PathVariable int id, @RequestBody VideoGame videoGame) {
        if (videoGame.getTitle() == null
                || videoGame.getDeveloper() == null
                || videoGame.getPublisher() == null
                || videoGame.getYear() == 0
                || videoGame.getRating() == null
                || videoGame.getGenre() == null
                || videoGame.getPlatform() == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("One or more required fields are null");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        VideoGame videoGameToUpdate = this.videoGameRepository.findById(id).orElse(null);
        if (videoGameToUpdate == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No video game by that id found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        videoGameToUpdate.setTitle(videoGame.getTitle());
        videoGameToUpdate.setDeveloper(videoGame.getDeveloper());
        videoGameToUpdate.setPlatform(videoGame.getPlatform());
        videoGameToUpdate.setYear(videoGame.getYear());
        videoGameToUpdate.setRating(videoGame.getRating());
        videoGameToUpdate.setGenre(videoGame.getGenre());
        videoGameToUpdate.setPlatform(videoGame.getPlatform());

        VideoGameResponse videoGameResponse = new VideoGameResponse();
        videoGameResponse.set(
                "Successfully updated video game",
                this.videoGameRepository.save(videoGameToUpdate)
        );
        return new ResponseEntity<>(videoGameResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteVideoGame(@PathVariable int id)    {
        VideoGame videoGameToDelete = this.videoGameRepository.findById(id).orElse(null);
        if(videoGameToDelete == null)
        {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No video game by that id found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        this.videoGameRepository.delete(videoGameToDelete);
        VideoGameResponse videoGameResponse = new VideoGameResponse();
        videoGameResponse.set(
                "Successfully deleted video game",
                videoGameToDelete
                );
        return ResponseEntity.ok(videoGameResponse);
    }
}
