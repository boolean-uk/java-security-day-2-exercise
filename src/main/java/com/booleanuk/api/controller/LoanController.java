package com.booleanuk.api.controller;

import com.booleanuk.api.model.Loan;
import com.booleanuk.api.model.User;
import com.booleanuk.api.model.VideoGame;
import com.booleanuk.api.repository.LoanRepository;
import com.booleanuk.api.repository.UserRepository;
import com.booleanuk.api.repository.VideoGameRepository;
import com.booleanuk.api.payload.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("loans")
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private VideoGameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    // Get full loan history
    @GetMapping
    public ResponseEntity<LoanListResponse> getAllLoans() {
        // Success response with list of loans
        LoanListResponse loanListResponse = new LoanListResponse();
        loanListResponse.set(this.loanRepository.findAll());
        return ResponseEntity.ok(loanListResponse);
    }

    // Get loan history of user
    @GetMapping("/user/{id}")
    public ResponseEntity<Response<?>> getAllLoansByUser(@PathVariable int id) {
        User user = this.userRepository.findById(id).orElse(null);
        // 404 Not found
        if (user == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("User not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        // Find all loans by user
        List<Loan> loans = this.loanRepository.findAll().stream().filter(loan -> loan.getUser() == user).toList();
        // Success response with list of loans
        LoanListResponse loanListResponse = new LoanListResponse();
        loanListResponse.set(loans);
        return ResponseEntity.ok(loanListResponse);
    }

    // Get loan history of video game
    @GetMapping("/videogame/{id}")
    public ResponseEntity<Response<?>> getAllLoansOfVideoGame(@PathVariable int id) {
        VideoGame game = this.gameRepository.findById(id).orElse(null);
        // 404 Not found
        if (game == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Video game not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        // Find all loans of video game
        List<Loan> loans = this.loanRepository.findAll().stream().filter(loan -> loan.getGame() == game).toList();
        // Success response with list of loans
        LoanListResponse loanListResponse = new LoanListResponse();
        loanListResponse.set(loans);
        return ResponseEntity.ok(loanListResponse);
    }

    // Get current loans
    @GetMapping("/borrowed")
    public ResponseEntity<LoanListResponse> getAllLoansStillBorrowed() {
        List<Loan> loans = this.loanRepository.findAll().stream()
                .filter(Loan::isBorrowed).toList();
        // Success response with list of loans
        LoanListResponse loanListResponse = new LoanListResponse();
        loanListResponse.set(loans);
        return ResponseEntity.ok(loanListResponse);
    }

    // Allow loan of video games
    @PostMapping("/videogame/{game_id}/user/{user_id}")
    public ResponseEntity<Response<?>> borrowGame(@PathVariable int game_id, @PathVariable int user_id) {
        VideoGame videoGame = this.gameRepository.findById(game_id).orElse(null);
        // 404 Not found video game
        if (videoGame == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Video game not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        User user = this.userRepository.findById(user_id).orElse(null);
        // 404 Not found user
        if (user == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("User not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        // New loan
        Loan loan = new Loan();
        loan.setGame(videoGame);
        loan.setUser(user);
        this.loanRepository.save(loan);
        // Success response with updated video game with the user borrowing it
        LoanResponse loanResponse = new LoanResponse();
        loanResponse.set(loan);
        return new ResponseEntity<>(loanResponse, HttpStatus.CREATED);
    }

    // Return borrowed game
    @PutMapping("/videogame/{game_id}/user/{user_id}")
    public ResponseEntity<Response<?>> returnGame(@PathVariable int game_id, @PathVariable int user_id) {
        VideoGame videoGame = this.gameRepository.findById(game_id).orElse(null);
        // 404 Not found video game
        if (videoGame == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Video game not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        User user = this.userRepository.findById(user_id).orElse(null);
        // 404 Not found user
        if (user == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("User not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Loan loan = this.loanRepository.findAll().stream()
                .filter(l -> l.getGame() == videoGame &&
                        l.getUser() == user &&
                        l.isBorrowed())
                .findFirst().orElse(null);
        if (loan == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Loan not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        // Update loan
        loan.setBorrowed(false);
        this.loanRepository.save(loan);
        // Success response with loan of returned game
        LoanResponse loanResponse = new LoanResponse();
        loanResponse.set(loan);
        return new ResponseEntity<>(loanResponse, HttpStatus.CREATED);
    }
}
