package com.booleanuk.api.library.controllers;

import com.booleanuk.api.library.models.Item;
import com.booleanuk.api.library.models.Loan;
import com.booleanuk.api.library.models.User;
import com.booleanuk.api.library.payload.response.*;
import com.booleanuk.api.library.repository.ItemRepository;
import com.booleanuk.api.library.repository.LoanRepository;
import com.booleanuk.api.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("loans")
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    private ErrorResponse errorResponse = new ErrorResponse();
    private LoanResponse loanResponse = new LoanResponse();
    private LoanListResponse loanListResponse = new LoanListResponse();

    @GetMapping("/user/{id}")
    public ResponseEntity<Response<?>> getAllLoansForAUser(@PathVariable int id) {
        User user = this.userRepository.findById(id).orElse(null);
        if (user == null) {
            errorResponse.set("user not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        loanListResponse.set(this.loanRepository.findByUser(user));
        return ResponseEntity.ok(loanListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createLoan(@RequestBody Loan loan) {
        try {
            loanResponse.set(this.loanRepository.save(loan));
        } catch (Exception e) {
            errorResponse.set("Bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(loanResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<Response<?>> updateLoan(@PathVariable int id, @RequestBody Loan loan) {
        Loan loanToUpdate = this.loanRepository.findById(id).orElse(null);
        if (loanToUpdate == null) {
            errorResponse.set("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        loanToUpdate.setReturnDate(LocalDateTime.now());
        Item itemOnLoan = loanToUpdate.getItem();
        itemOnLoan.setOnLoan(false);

        try {
            loanToUpdate = this.loanRepository.save(loanToUpdate);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        LoanResponse loanResponse = new LoanResponse();
        loanResponse.set(loanToUpdate);
        return new ResponseEntity<>(loanResponse, HttpStatus.CREATED);
    }
}
