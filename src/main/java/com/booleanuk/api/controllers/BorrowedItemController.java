package com.booleanuk.api.controllers;

import com.booleanuk.api.models.BorrowedItem;
import com.booleanuk.api.models.Item;
import com.booleanuk.api.models.User;
import com.booleanuk.api.repository.BorrowedItemRepository;
import com.booleanuk.api.repository.ItemRepository;
import com.booleanuk.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("borrow")
public class BorrowedItemController {
    @Autowired
    BorrowedItemRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;
/*
    @GetMapping("/users/{userId}")
    public ResponseEntity<?> getAllForUser(@PathVariable int userId) {
        return ResponseEntity.ok(new Response<>(this.repository.findBorrowedGamesByUserId(userId)));
    }

    @GetMapping("/games/{gameId}")
    public ResponseEntity<Response<List<BorrowedGameDto>>> getAllForGame(@PathVariable int gameId) {
        return ResponseEntity.ok(new Response<>(this.repository.findBorrowedGamesByGameId(gameId)));
    }

    @PostMapping("/users/{userId}/games/{gameId}")
    public ResponseEntity<Response<BorrowedGameDto>> lendGame(@PathVariable int userId, @PathVariable int gameId) {
        // Check if game is currently lent out
        if (!this.repository.findIsBorrowedByGameId(gameId).isEmpty()) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Game is already lent out");
        }
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "user not found"));
        Game game = this.gameRepository.findById(gameId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "game not found"));
        if (user.getDob().isAfter(LocalDate.now().minusYears(game.getAgeRating()))) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "User is too young!!!");
        }
        BorrowedGame createdBorrowedGame = this.repository.save(new BorrowedGame(user, game));

        return new ResponseEntity<>(new Response<>(this.translateToDto(createdBorrowedGame)), HttpStatus.CREATED);
    }

    @PutMapping("/users/{userId}/games/{gameId}")
    public ResponseEntity<Response<BorrowedGameDto>> returnGame(@PathVariable int userId, @PathVariable int gameId) {
        List<BorrowedGame> borrowedGames = this.repository.findIsBorrowedByGameId(gameId);
        if (borrowedGames.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "game not found");
        } else if (borrowedGames.get(0).getUser().getId() != userId) {
            throw new ApiException(HttpStatus.NOT_FOUND, "Game not lent to this user");
        }
        BorrowedGame borrowedGame = borrowedGames.get(0);
        borrowedGame.setReturnDate(LocalDate.now());

        return new ResponseEntity<>(new Response<>(this.translateToDto(this.repository.save(borrowedGame))), HttpStatus.CREATED);
    }

    public BorrowedGameDto translateToDto(BorrowedGame borrowedGame) {
        return new BorrowedGameDto(borrowedGame.getId(), borrowedGame.getGame().getTitle(), borrowedGame.getUser().getUsername(), borrowedGame.getBorrowDate(), borrowedGame.getReturnDate());
    }*/
}
