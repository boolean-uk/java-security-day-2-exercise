package com.booleanuk.api.controllers;


import com.booleanuk.api.models.VideoGame;
import com.booleanuk.api.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/videogames")
public class VideoGameController {

    @Autowired
    private VideoGameRepository videoGameRepository;

    @PostMapping
    public ResponseEntity<VideoGame> createVideoGame(@RequestBody VideoGame videoGame) {
        return new ResponseEntity<>(this.videoGameRepository.save(videoGame), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VideoGame>> getAllVideoGames() {
        return ResponseEntity.ok(this.videoGameRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoGame> getOneVideoGame(@PathVariable int id) {
        VideoGame videoGame = this.videoGameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video Game with ID " + id + " not found."));
        return ResponseEntity.ok(videoGame);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoGame> updateVideoGame(@PathVariable int id, @RequestBody VideoGame videoGameDetails) {
        VideoGame videoGameToUpdate = this.videoGameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video Game with ID " + id + " not found."));

        videoGameToUpdate.setTitle(videoGameDetails.getTitle());
        videoGameToUpdate.setGameStudio(videoGameDetails.getGameStudio());
        videoGameToUpdate.setAgeRating(videoGameDetails.getAgeRating());
        videoGameToUpdate.setNumberOfPlayers(videoGameDetails.getNumberOfPlayers());
        videoGameToUpdate.setGenre(videoGameDetails.getGenre());

        return new ResponseEntity<>(this.videoGameRepository.save(videoGameToUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideoGame(@PathVariable int id) {
        VideoGame videoGameToDelete = this.videoGameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video Game with ID " + id + " not found."));
        this.videoGameRepository.delete(videoGameToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
