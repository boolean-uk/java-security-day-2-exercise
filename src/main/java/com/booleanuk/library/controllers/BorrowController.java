package com.booleanuk.library.controllers;

import com.booleanuk.library.models.Borrow;
import com.booleanuk.library.models.Item;
import com.booleanuk.library.models.User;
import com.booleanuk.library.repository.BorrowRepository;
import com.booleanuk.library.repository.ItemRepository;
import com.booleanuk.library.repository.UserRepository;
import com.booleanuk.library.payload.response.BorrowListResponse;
import com.booleanuk.library.payload.response.BorrowResponse;
import com.booleanuk.library.payload.response.ErrorResponse;
import com.booleanuk.library.payload.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
public class BorrowController {
    @Autowired
    BorrowRepository borrowRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ItemRepository itemRepository;

    @GetMapping("users/{userID}/borrows")
    public ResponseEntity<Response<?>> getBorrows(@PathVariable int userID){
        User user = this.userRepository.findById(userID).orElse(null);
        if (user == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No user with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        List<Borrow> borrows = this.borrowRepository.findByUserId(userID);
        if (borrows.isEmpty()) {
            ErrorResponse error = new ErrorResponse();
            error.set("No borrows found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        BorrowListResponse borrowListResponse = new BorrowListResponse();
        borrowListResponse.set(borrows);
        return ResponseEntity.ok(borrowListResponse);
    }

    // for renting.
    @PostMapping("items/{itemID}/users/{userID}")
    public ResponseEntity<Response<?>> addBorrow(@PathVariable int itemID, @PathVariable int userID, @RequestBody Borrow borrow){
        Item item = this.itemRepository.findById(itemID).orElse(null);
        if (item == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No item with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        User user = this.userRepository.findById(userID).orElse(null);
        if (user == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No user with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Borrow borrow1 = new Borrow();
        try {
            borrow1.setBorrowDate(ZonedDateTime.now());
            borrow1.setReturnDate(ZonedDateTime.now().plusDays(7));
            borrow1.setItem(item);
            borrow1.setUser(user);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not create borrow, check fields");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        if (borrow1.getItem().getIsBorrowed()){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Game is not available");
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        }
        try {
            item.setIsBorrowed(true);
        } catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could update borrow state of game");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        this.borrowRepository.save(borrow1);
        BorrowResponse borrowResponse = new BorrowResponse();
        borrowResponse.set(borrow1);
        return new ResponseEntity<>(borrowResponse, HttpStatus.CREATED);
    }


    // For delivering mostly...
    @PutMapping("items/{itemID}/users/{userID}")
    public ResponseEntity<Response<?>> updateBorrow(@PathVariable int itemID, @PathVariable int userID, @RequestBody Borrow borrow) {
        Item item = this.itemRepository.findById(itemID).orElse(null);
        if (item == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No game with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        User user = this.userRepository.findById(userID).orElse(null);
        if (user == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No user with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Borrow borrow1 = new Borrow();
        try {
            borrow1.setBorrowDate(borrow.getBorrowDate());
            borrow1.setReturnDate(ZonedDateTime.now());
            borrow1.setItem(item);
            borrow1.setUser(user);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not update borrow, please check all fields are correct.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        if (borrow1.getItem().getIsBorrowed()){
            try {
                item.setIsBorrowed(false);
        } catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could update borrow state of game");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }}
        BorrowResponse borrowResponse = new BorrowResponse();
        borrowResponse.set(borrow1);
        return new ResponseEntity<>(borrowResponse, HttpStatus.CREATED);
    }

}
