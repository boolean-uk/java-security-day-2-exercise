package com.booleanuk.api.controller;

import com.booleanuk.api.model.Item;
import com.booleanuk.api.model.Loan;
import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.ItemRepository;
import com.booleanuk.api.repository.LoanRepository;
import com.booleanuk.api.repository.UserRepository;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.LoanListResponse;
import com.booleanuk.api.response.LoanResponse;
import com.booleanuk.api.response.Response;
import com.booleanuk.api.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items/{itemId}/users/{userId}")
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getLoansByItemAndUser(@PathVariable("itemId") int itemId, @PathVariable("userId") int userId) {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MODERATOR"))) {
            if (userId != userDetails.getId()) {
                ErrorResponse error = new ErrorResponse();
                error.set("Unauthorized to access this resource");
                return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
            }
        }
        User user = this.userRepository.findById(userId).orElse(null);
        if (user == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("User not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        Item item = this.itemRepository.findById(itemId).orElse(null);
        if (item == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Item not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        LoanListResponse loanListResponse = new LoanListResponse();
        loanListResponse.set(this.loanRepository.findByUserAndItem(user, item));
        return ResponseEntity.ok(loanListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createLoan(@PathVariable("itemId") int itemId, @PathVariable("userId") int userId, @RequestBody Loan loan) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MODERATOR"))) {
            if (userId != userDetails.getId()) {
                ErrorResponse error = new ErrorResponse();
                error.set("Unauthorized to access this resource");
                return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
            }
        }

        User user = this.userRepository.findById(userId).orElse(null);
        if (user == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("User not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        Item item = this.itemRepository.findById(itemId).orElse(null);
        if (item == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Item not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        if (!this.loanRepository.findByReturnedAndItem(false, item).isEmpty()) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Item already loaned out");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        LoanResponse loanResponse = new LoanResponse();
        loan.setItem(item);
        loan.setUser(user);
        this.loanRepository.save(loan);
        loanResponse.set(loan);
        return new ResponseEntity<>(loanResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> returnItem(@PathVariable("itemId") int itemId, @PathVariable("userId") int userId, @PathVariable("id") int id) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MODERATOR"))) {
            if (id != userDetails.getId()) {
                ErrorResponse error = new ErrorResponse();
                error.set("Unauthorized to access this resource");
                return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
            }
        }
        User user = this.userRepository.findById(userId).orElse(null);
        if (user == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("User not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        Item item = this.itemRepository.findById(itemId).orElse(null);
        if (item == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Item not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        Loan loan = this.loanRepository.findByIdAndUserAndItem(id, user, item);
        if (loan == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Loan not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        loan.setReturned(true);
        this.loanRepository.save(loan);
        LoanResponse loanResponse = new LoanResponse();
        loanResponse.set(loan);
        return new ResponseEntity<>(loanResponse, HttpStatus.CREATED);
    }
}
