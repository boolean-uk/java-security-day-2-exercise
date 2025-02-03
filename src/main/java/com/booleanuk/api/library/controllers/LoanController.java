package com.booleanuk.api.library.controllers;

import com.booleanuk.api.library.models.Item;
import com.booleanuk.api.library.models.Loan;
import com.booleanuk.api.library.models.User;
import com.booleanuk.api.library.payload.response.ErrorResponse;
import com.booleanuk.api.library.payload.response.Response;
import com.booleanuk.api.library.repository.ItemRepository;
import com.booleanuk.api.library.repository.LoanRepository;
import com.booleanuk.api.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping
public class LoanController {
    @Autowired private LoanRepository loanRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ItemRepository itemRepository;

    // see all loans: admin
    @GetMapping("/admin/loans")
    public ResponseEntity<Response<List<Loan>>> getAllLoans() {
        return ResponseEntity.ok(new Response<>("success", this.loanRepository.findAll()));
    }

    // see user's loans: user, admin
    @GetMapping("/user/{username}/loans")
    public ResponseEntity<Response<List<Loan>>> getUserLoans(@PathVariable String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return null;  // temp errorhandling

        return new ResponseEntity<>(new Response<>("success", loanRepository.findAll().stream().filter(
                l -> l.getUserId() == user.getId()
        ).toList()), HttpStatus.OK);
    }

    // see user's active loans: user, admin
    @GetMapping("/user/{username}/loans/active")
    public ResponseEntity<Response<List<Loan>>> getUserActiveLoans(@PathVariable String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) return null;

        return new ResponseEntity<>(new Response<>("success", loanRepository.findAll().stream().filter(
                l -> l.getUserId() == user.getId() && !l.isReturned()
        ).toList()), HttpStatus.OK);
    }

    public record LoanRequest(int itemId, int userId) {}

    // loan item: user, admin
    @PostMapping("/user/loans")
    public ResponseEntity<Response<?>> loanItem(@RequestBody LoanRequest loanRequest) {
        User user = userRepository.findById(loanRequest.userId).orElse(null);
        Item item = itemRepository.findById(loanRequest.itemId).orElse(null);

        if (user == null || item == null) {
            return new ResponseEntity<>(new ErrorResponse("not found"), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new Response<>("success", loanRepository.save(new Loan(user, item, false, LocalDateTime.now()))), HttpStatus.CREATED);
    }

    // return item: user, admin
    @PutMapping("/user/{username}/loans/{loan_id}")
    public ResponseEntity<Response<?>> returnItem(@PathVariable(name="username") String username, @PathVariable(name="loan_id") int loanId) {
        User user = userRepository.findByUsername(username).orElse(null);
        Loan loan = loanRepository.findById(loanId).orElse(null);
        if (loan == null || !loan.getUser().equals(user)) return new ResponseEntity<>(new ErrorResponse("not found"), HttpStatus.NOT_FOUND);
        loan.setReturned(true);
        loanRepository.save(loan);
        return new ResponseEntity<>(new Response<>("success", loan), HttpStatus.CREATED);
    }


}
