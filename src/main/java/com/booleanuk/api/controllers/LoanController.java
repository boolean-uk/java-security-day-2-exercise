package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Loan;
import com.booleanuk.api.models.User;
import com.booleanuk.api.models.VideoGame;
import com.booleanuk.api.payload.responses.ErrorResponse;
import com.booleanuk.api.payload.responses.Response;
import com.booleanuk.api.repositories.LoanRepository;
import com.booleanuk.api.repositories.UserRepository;
import com.booleanuk.api.repositories.VideoGameRepository;

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
    private VideoGameRepository videoGameRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAll(){
        Response<List<Loan>> response = new Response<>();
        response.set(this.loanRepository.findAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getOne(@PathVariable int id){
        Loan loan = findTheLoan(id);
        if (loan == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        Response<Loan> response = new Response<>();
        response.set(loan);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<?>> addLoan(@RequestBody Loan loan, @RequestParam int user_id,
                                               @RequestParam int video_game_id){
        User tempUser = this.userRepository.findById(user_id)
                .orElse(null);
        VideoGame tempGame = this.videoGameRepository.findById(video_game_id)
                .orElse(null);
        if (tempUser == null ||
                tempGame == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        if (loan.getEndDate() == null ||
                loan.getStartDate() == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        loan.setUser(tempUser);
        loan.setVideoGame(tempGame);
        Response<Loan> response = new Response<>();
        response.set(this.loanRepository.save(loan));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateLoan(@PathVariable int id, @RequestBody Loan loan,
                                                  @RequestParam int user_id, @RequestParam int video_game_id){
        User tempUser = this.userRepository.findById(user_id)
                .orElse(null);
        VideoGame tempGame = this.videoGameRepository.findById(video_game_id)
                .orElse(null);

        Loan updateLoan = findTheLoan(id);
        if (updateLoan == null ||
        tempUser == null ||
        tempGame == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        if (loan.getStartDate() == null ||
                loan.getEndDate() == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        updateLoan.setStartDate(loan.getStartDate());
        updateLoan.setEndDate(loan.getEndDate());
        updateLoan.setVideoGame(tempGame);
        updateLoan.setUser(tempUser);

        Response<Loan> response = new Response<>();
        response.set(this.loanRepository.save(updateLoan));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteLoan(@PathVariable int id){
        Loan deleteLoan = findTheLoan(id);
        if (deleteLoan == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        this.loanRepository.delete(deleteLoan);
        Response<Loan> response = new Response<>();
        response.set(deleteLoan);
        return ResponseEntity.ok(response);
    }

    private Loan findTheLoan(int id){
        return this.loanRepository.findById(id)
                .orElse(null);
    }
}
