package com.booleanuk.library.controller;

import com.booleanuk.library.model.VideoGame;
import com.booleanuk.library.repository.LoanRepository;
import com.booleanuk.library.repository.VideoGameRepository;
import com.booleanuk.library.response.ErrorResponse;
import com.booleanuk.library.response.Response;
import com.booleanuk.library.response.VideoGameListResponse;
import com.booleanuk.library.response.VideoGameResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("videogames")
public class VideoGameController {
    @Autowired
    private VideoGameRepository videoGameRepository;
    @Autowired
    private LoanRepository loanRepository;

    @GetMapping
    public ResponseEntity<VideoGameListResponse> getAllVideoGames() {
        VideoGameListResponse videoGameListResponse = new VideoGameListResponse();
        videoGameListResponse.set(this.videoGameRepository.findAll());
        return ResponseEntity.ok(videoGameListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getVideoGameById(@PathVariable int id) {
        VideoGame videoGame = this.videoGameRepository.findById(id).orElse(null);
        if (videoGame == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        VideoGameResponse videoGameResponse = new VideoGameResponse();
        videoGameResponse.set(videoGame);
        return ResponseEntity.ok(videoGameResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createVideoGame(@RequestBody VideoGame videoGame) {
        if (this.areAnyFieldsBad(videoGame)) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        this.videoGameRepository.save(videoGame);
        VideoGameResponse videoGameResponse = new VideoGameResponse();
        videoGameResponse.set(videoGame);
        return new ResponseEntity<>(videoGameResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteVideoGame(@PathVariable int id) {
        if (this.doesVideoGameIDNotExist(id)) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        VideoGame videoGameToDelete = this.getVideoGameByID(id);
        if (videoGameToDelete.getLoan() != null) {
           this.loanRepository.delete(videoGameToDelete.getLoan());
        }
        this.videoGameRepository.delete(videoGameToDelete);
        VideoGameResponse videoGameResponse = new VideoGameResponse();
        videoGameResponse.set(videoGameToDelete);
        return ResponseEntity.ok(videoGameResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateVideoGame(@PathVariable int id, @RequestBody VideoGame videoGame) {
        ErrorResponse error = new ErrorResponse();
        if (videoGame == null || (videoGame.getTitle() == null && videoGame.getStudio() == null
                && videoGame.getRating() == null && videoGame.getNumberOfPlayers() == 0 && videoGame.getGenre() == null)) {
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        if (doesVideoGameIDNotExist(id)) {
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        VideoGame videoGameToUpdate = this.getVideoGameByID(id);
        if (videoGame.getTitle() != null && !videoGame.getTitle().isBlank()) {
            videoGameToUpdate.setTitle(videoGame.getTitle());
        }
        if (videoGame.getStudio() != null && !videoGame.getStudio().isBlank()) {
            videoGameToUpdate.setStudio(videoGame.getStudio());
        }
        if (videoGame.getRating() != null && !videoGame.getRating().isBlank()) {
            videoGameToUpdate.setRating(videoGame.getRating());
        }
        if (videoGame.getNumberOfPlayers() != 0) {
            videoGameToUpdate.setNumberOfPlayers(videoGame.getNumberOfPlayers());
        }
        if (videoGame.getGenre() != null && !videoGame.getGenre().isBlank()) {
            videoGameToUpdate.setGenre(videoGame.getGenre());
        }
        this.videoGameRepository.save(videoGameToUpdate);
        VideoGameResponse videoGameResponse = new VideoGameResponse();
        videoGameResponse.set(videoGameToUpdate);
        return new ResponseEntity<>(videoGameResponse, HttpStatus.CREATED);
    }

    private boolean doesVideoGameIDNotExist(int id) {
        for (VideoGame videoGame : this.videoGameRepository.findAll()) {
            if (videoGame.getId() == id) {
                return false;
            }
        }
        return true;
    }

    private VideoGame getVideoGameByID(int id) {
        for (VideoGame videoGame : this.videoGameRepository.findAll()) {
            if (videoGame.getId() == id) {
                return videoGame;
            }
        }
        return new VideoGame();
    }

    private boolean areAnyFieldsBad(VideoGame videoGame) {
        if (videoGame.getTitle() == null ||
                videoGame.getStudio() == null ||
                videoGame.getRating() == null ||
                videoGame.getNumberOfPlayers() == 0 ||
                videoGame.getGenre() == null ||
                videoGame.getTitle().isBlank() ||
                videoGame.getStudio().isBlank() ||
                videoGame.getRating().isBlank() ||
                videoGame.getGenre().isBlank())
        {
            return true;
        }
        return false;
    }
}
