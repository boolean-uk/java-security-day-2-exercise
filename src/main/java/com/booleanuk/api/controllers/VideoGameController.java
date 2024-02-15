package com.booleanuk.api.controllers;

import com.booleanuk.api.models.VideoGame;
import com.booleanuk.api.repositories.VideoGameRepository;
import com.booleanuk.api.payload.responses.ErrorResponse;
import com.booleanuk.api.payload.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("video_games")
public class VideoGameController {
    @Autowired
    private VideoGameRepository videoGameRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAll(){
        Response<List<VideoGame>> response = new Response<>();
        response.set(this.videoGameRepository.findAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getOne(@PathVariable int id){
        VideoGame game = findTheGame(id);
        if (game == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        Response<VideoGame> response = new Response<>();
        response.set(game);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<?>> addGame(@RequestBody VideoGame game){
        if (game.getTitle() == null ||
        game.getGenre() == null ||
        game.getGame_studio() == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        Response<VideoGame> response = new Response<>();
        response.set(this.videoGameRepository.save(game));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateGame(@PathVariable int id, @RequestBody VideoGame game){
        VideoGame updateGame = findTheGame(id);
        if (updateGame == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("not found");
           return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        if (game.getTitle() == null ||
                game.getGenre() == null ||
                game.getGame_studio() == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        updateGame.setTitle(game.getTitle());
        updateGame.setGenre(game.getGenre());
        updateGame.setGame_studio(game.getGame_studio());
        updateGame.setAge_rating(game.getAge_rating());
        updateGame.setNumberOfPlayers(game.getNumberOfPlayers());

        Response<VideoGame> response = new Response<>();
        response.set(this.videoGameRepository.save(updateGame));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteGame(@PathVariable int id){
        VideoGame deleteGame = findTheGame(id);
        if (deleteGame == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        this.videoGameRepository.delete(deleteGame);
        Response<VideoGame> response = new Response<>();
        response.set(deleteGame);
        return ResponseEntity.ok(response);
    }

    private VideoGame findTheGame(int id){
        return this.videoGameRepository.findById(id)
                .orElse(null);
    }
}
