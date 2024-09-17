package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Loan;
import com.booleanuk.api.payload.response.Response;
import com.booleanuk.api.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/loans")
public class LoanController {
    @Autowired
    private LoanService loanService;

    Response<Object> response = new Response<>();

    @PostMapping("/borrow")
    public ResponseEntity<?> borrowItem(@RequestBody Loan loan) {
        if (Objects.isNull(loan.getUser()) || Objects.isNull(loan.getItem())) {
            response.setError("User and item are required");
            return ResponseEntity.badRequest().body(response);
        }
        response.setSuccess(loanService.borrowItem(loan));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/return/{id}")
    public ResponseEntity<Loan> returnItem(@PathVariable Integer id) {
        return ResponseEntity.ok(loanService.returnItem(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Loan>> getUserLoans(@PathVariable Integer userId) {
        return ResponseEntity.ok(loanService.getUserLoans(userId));
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<Loan>> getItemLoans(@PathVariable Integer itemId) {
        return ResponseEntity.ok(loanService.getItemLoans(itemId));
    }
}