package com.booleanuk.api.controller;

import com.booleanuk.api.model.Loan;
import com.booleanuk.api.model.User;
import com.booleanuk.api.model.VideoGame;
import com.booleanuk.api.repository.LoanRepository;
import com.booleanuk.api.repository.UserRepository;
import com.booleanuk.api.repository.VideoGameRepository;
import com.booleanuk.payload.response.ErrorResponse;
import com.booleanuk.payload.response.LoanListResponse;
import com.booleanuk.payload.response.LoanResponse;
import com.booleanuk.payload.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("loans")
public class LoanController {
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VideoGameRepository videoGameRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAllLoans()    {
        LoanListResponse loanListResponse = new LoanListResponse();
        loanListResponse.set(
                "Successfully returned a list of all loans",
                this.loanRepository.findAll()
        );
        return ResponseEntity.ok(loanListResponse);
    }

    @GetMapping("active")
    public ResponseEntity<Response<?>> getAllActiveLoans()  {
        LoanListResponse loanListResponse = new LoanListResponse();
        loanListResponse.set(
                "Successfully returned a list of all active loans",
                this.loanRepository.findByReturnedFalse()
        );
        return ResponseEntity.ok(loanListResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getOneLoan(@PathVariable int id) {
        Loan loan = this.loanRepository.findById(id).orElse(null);
        if(loan == null)
        {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No loan with that id found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        LoanResponse loanResponse = new LoanResponse();
        loanResponse.set("Successfully returned loan", loan);
        return ResponseEntity.ok(loanResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createLoan(
        @RequestParam int userId,
        @RequestParam int videoGameId
    )    {
        User loaner = this.userRepository.findById(userId).orElse(null);
        if(loaner == null)
        {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No user with that id were found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        VideoGame videoGameLoaned = this.videoGameRepository.findById(videoGameId).orElse(null);
        if(videoGameLoaned == null)
        {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No videogame with that id were found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<Loan> activeLoansOfGame = this.loanRepository.findAllByVideoGameId(videoGameId);
        if(!activeLoansOfGame.isEmpty())
        {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("This game is not available for loan at the moment");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        Loan loan = new Loan(
                loaner,
                videoGameLoaned,
                ZonedDateTime.now(),
                ZonedDateTime.now().plusDays(30),
                false
        );

        this.loanRepository.save(loan);
        LoanResponse loanResponse = new LoanResponse();
        loanResponse.set("Successfully created loan", loan);
        return new ResponseEntity<>(loanResponse, HttpStatus.CREATED);
    }

    @PutMapping("{id}/return")
    public ResponseEntity<Response<?>> returnVideoGame(@PathVariable int id) {
        Loan loanToReturn = this.loanRepository.findById(id).orElse(null);
        if(loanToReturn == null)
        {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No loan with that id found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        loanToReturn.setReturned(true);
        this.loanRepository.save(loanToReturn);
        LoanResponse loanResponse = new LoanResponse();
        loanResponse.set("Successfully returned video game", loanToReturn);
        return new ResponseEntity<>(loanResponse, HttpStatus.CREATED);
    }
}
