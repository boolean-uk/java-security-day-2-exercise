package com.booleanuk.api.library.controllers;


import com.booleanuk.api.library.models.Borrow;
import com.booleanuk.api.library.models.Item;
import com.booleanuk.api.library.models.User;
import com.booleanuk.api.library.payload.response.*;
import com.booleanuk.api.library.repository.BorrowRepository;
import com.booleanuk.api.library.repository.ItemRepository;
import com.booleanuk.api.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("borrows")
public class BorrowController {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    @GetMapping("users/{userID}/borrows")
    public ResponseEntity<?> getBorrow(@PathVariable int userID) {
        User user = this.userRepository.findById(userID).orElse(null);
        if(user == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No user");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        List<Borrow> borrows = this.borrowRepository.findByUserId(userID);
        if(borrows.isEmpty()){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No borrows");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        BorrowListResponse borrowListResponse = new BorrowListResponse();
        borrowListResponse.set(this.borrowRepository.findAll());
        return ResponseEntity.ok(borrowListResponse);
    }

    @PostMapping("items/{itemID}/users/{userID}")
    public ResponseEntity<Response<?>> createBorrow(@PathVariable int itemID, @PathVariable int userID, @RequestBody Borrow borrow){
        Item item = this.itemRepository.findById(itemID).orElse(null);
        if (item == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found item");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        User user = this.userRepository.findById(userID).orElse(null);
        if (user == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found user");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        Borrow borrows = new Borrow();
        try {
            borrows.setBorrowed(ZonedDateTime.now());
            borrows.setReturned(ZonedDateTime.now());
            borrows.setItem(item);
            borrows.setUser(user);
        }catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not create borrow");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        if (borrows.getItem().getIsBorrowed()){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Already borrowed");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } try {
            item.setIsBorrowed(true);
        } catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not update borrow item");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        this.borrowRepository.save(borrows);
        BorrowResponse borrowResponse = new BorrowResponse();
        borrowResponse.set(borrows);
        return new ResponseEntity<>(borrowResponse, HttpStatus.CREATED);
    }

    @PutMapping("items/{itemID}/users/{userID}")
    public ResponseEntity<Response<?>> update(@PathVariable int itemID, @PathVariable int userID, @RequestBody Borrow borrow){
        Item item = this.itemRepository.findById(itemID).orElse(null);
        if (item == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found item with this id");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        User user = this.userRepository.findById(userID).orElse(null);
        if (user == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found user with this id");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        Borrow borrows = new Borrow();
        try {
            borrows.setBorrowed(ZonedDateTime.now());
            borrows.setReturned(ZonedDateTime.now());
            borrows.setItem(item);
            borrows.setUser(user);
        }catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not update borrow");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        if(borrows.getItem().getIsBorrowed()){
            try {
                item.setIsBorrowed(false);
            } catch (Exception e){
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.set("Cant update borrow for game");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }}
        BorrowResponse borrowResponse = new BorrowResponse();
        borrowResponse.set(borrows);
        return new ResponseEntity<>(borrowResponse, HttpStatus.CREATED);
    }

}

