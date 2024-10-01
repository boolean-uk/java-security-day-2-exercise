package com.booleanuk.api.Library.Controller;

import com.booleanuk.api.Library.Model.Game;
import com.booleanuk.api.Library.Repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("games")
public class GameController {
    @Autowired
    private GameRepository gameRepository;

    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody Game game){


        try {

            return new ResponseEntity<Game>(this.gameRepository.save(game),
                    HttpStatus.CREATED);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create game, please check all required fields are correct.");
        }


    }

    @GetMapping
    public ResponseEntity<List<Game>> getAllGames(){
        return ResponseEntity.ok(this.gameRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Game> getOneGame(@PathVariable int id){
        Game game=this.gameRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        return ResponseEntity.ok(game);
    }

    @PutMapping("{id}")
    public ResponseEntity<Game> updateGame(@PathVariable int id,
                                           @RequestBody Game game){
        Game gameToUpdate=this.gameRepository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No game with that ID found")
        );
        gameToUpdate.setTitle(game.getTitle());
        gameToUpdate.setGameStudio(game.getGameStudio());
        gameToUpdate.setGenre(game.getGenre());
        gameToUpdate.setAgeRating(game.getAgeRating());
        gameToUpdate.setPlayerNumber(game.getPlayerNumber());
        return new ResponseEntity<Game>(this.gameRepository.save(gameToUpdate
        ), HttpStatus.CREATED);



    }

    @DeleteMapping("{id}")
    public ResponseEntity<Game> deleteGame(@PathVariable int id){
        Game gameToDelete=this.gameRepository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No book with that ID found")
        );

        this.gameRepository.delete(gameToDelete);
        return ResponseEntity.ok(gameToDelete);
    }
}
