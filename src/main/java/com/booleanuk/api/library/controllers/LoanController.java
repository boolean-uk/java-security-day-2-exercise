package com.booleanuk.api.library.controllers;

import com.booleanuk.api.library.models.*;
import com.booleanuk.api.library.payload.response.ErrorResponse;
import com.booleanuk.api.library.payload.response.LoanListResponse;
import com.booleanuk.api.library.payload.response.Response;
import com.booleanuk.api.library.repository.ItemRepository;
import com.booleanuk.api.library.repository.LoanRepository;
import com.booleanuk.api.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("loans")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;


    @GetMapping("/users/{userId}")
    public ResponseEntity<Response<?>> getLoansForUser(@PathVariable int userId) {
        List<Loan> loans = new ArrayList<>();
        User user = getAUser(userId);

        if (user == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No User with that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        for (Role role : user.getRoles()) {
            if (role.getName() == ERole.ROLE_USER) {
                for (Loan loan : this.loanRepository.findAll()) {
                    if (loan.getUser() == user) {
                        loans.add(loan);
                    }
                }

                LoanListResponse loanListResponse = new LoanListResponse();
                loanListResponse.set(loans);
                return ResponseEntity.ok(loanListResponse);
            }
        }

        ErrorResponse error = new ErrorResponse();
        error.set("No User with that id were found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    @GetMapping("/items/{itemId}")
    public ResponseEntity<Response<?>> getLoansForItem(@PathVariable int itemId) {
        List<Loan> loans = new ArrayList<>();
        Item item = getAItem(itemId);

        if (item == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No Item with that id were found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        for (Loan loan : this.loanRepository.findAll()) {
            if (loan.getItem() == item) {
                loans.add(loan);
            }
        }

        LoanListResponse loanListResponse = new LoanListResponse();
        loanListResponse.set(loans);
        return ResponseEntity.ok(loanListResponse);
    }

    @GetMapping()
    public ResponseEntity<Response<?>> getLoansForCurrentUSer(@AuthenticationPrincipal UserDetails userDetails) {
        List<Loan> loans = new ArrayList<>();
        Optional<User> userOptional = userRepository.findByUsername(userDetails.getUsername());
        User user = userOptional.orElseThrow(() -> new NoSuchElementException("User not found"));

        for (Loan loan : this.loanRepository.findAll()) {
            if (loan.getUser() == user) {
                loans.add(loan);
            }
        }

        LoanListResponse loanListResponse = new LoanListResponse();
        loanListResponse.set(loans);
        return ResponseEntity.ok(loanListResponse);
    }


    private User getAUser(int id) {
        return this.userRepository.findById(id).orElse(null);
    }

    private Item getAItem(int id) {
        return this.itemRepository.findById(id).orElse(null);
    }
}
