package com.booleanuk.library.controllers;

import com.booleanuk.library.models.Loan;
import com.booleanuk.library.repository.LoanRepository;
import com.booleanuk.library.payload.response.ErrorResponse;
import com.booleanuk.library.payload.response.LoanListResponse;
import com.booleanuk.library.payload.response.LoanResponse;
import com.booleanuk.library.payload.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;

    @GetMapping
    public ResponseEntity<LoanListResponse> getAllLoans() {
        LoanListResponse response = new LoanListResponse();
        response.set(loanRepository.findAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getLoanById(@PathVariable int id) {
        Loan loan = loanRepository.findById(id).orElse(null);
        if (loan == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Loan not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        LoanResponse loanResponse = new LoanResponse();
        loanResponse.set(loan);
        return ResponseEntity.ok(loanResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createLoan(@RequestBody Loan loan) {
        LoanResponse loanResponse = new LoanResponse();
        try {
            loanResponse.set(loanRepository.save(loan));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(loanResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateLoan(@PathVariable int id, @RequestBody Loan loanDetails) {
        Loan loan = loanRepository.findById(id).orElse(null);
        if (loan == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Loan not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        // Update loan details
        loan.setReturnDate(loanDetails.getReturnDate());
        loan.setStatus(loanDetails.getStatus());
        LoanResponse loanResponse = new LoanResponse();
        try {
            loanResponse.set(loanRepository.save(loan));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(loanResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteLoan(@PathVariable int id) {
        Loan loan = loanRepository.findById(id).orElse(null);
        if (loan == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("Loan not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        loanRepository.delete(loan);
        LoanResponse loanResponse = new LoanResponse();
        loanResponse.set(loan);
        return ResponseEntity.ok(loanResponse);
    }
}