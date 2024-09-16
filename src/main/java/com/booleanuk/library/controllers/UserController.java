package com.booleanuk.library.controllers;

import com.booleanuk.library.models.Item;
import com.booleanuk.library.models.User;
import com.booleanuk.library.payload.response.ErrorResponse;
import com.booleanuk.library.payload.response.ItemListResponse;
import com.booleanuk.library.payload.response.ItemResponse;
import com.booleanuk.library.payload.response.Response;
import com.booleanuk.library.repository.ItemRepository;
import com.booleanuk.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users/bookings")
public class UserController {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("{id}")
    public ResponseEntity<?> bookItem(@PathVariable int id)   {
        Item itemToBook = this.itemRepository.findById(id).orElse(null);
        if (itemToBook == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User userBorrowing = this.userRepository.findByUsername(currentPrincipalName).orElse(null);
        if (userBorrowing == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("User not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        List<Item> items = userBorrowing.getBorrowedItems();
        userBorrowing.setBorrowedItems(items);

        itemToBook.setUserBorrowing(userBorrowing);
        return ResponseEntity.ok(itemToBook);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> returnItem(@PathVariable int id)   {
        Item itemToReturn = this.itemRepository.findById(id).orElse(null);
        if (itemToReturn == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User userReturning = this.userRepository.findByUsername(currentPrincipalName).orElse(null);
        if (userReturning == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("User not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        List<Item> items = userReturning.getBorrowedItems();
        items.remove(itemToReturn);
        userReturning.setBorrowedItems(items);

        itemToReturn.setUserBorrowing(null);
        return ResponseEntity.ok(itemToReturn);
    }

    /*
    TODO
        Fix query for getting all bookings by a user
     */
    @GetMapping
    public ResponseEntity<Response<?>> getBookById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User userBookings = this.userRepository.findByUsername(currentPrincipalName).orElse(null);
        if (userBookings == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("User not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        List<Item> borrowedItems = this.itemRepository.findByUserBorrowing(userBookings.getId());
        ItemListResponse itemListResponse = new ItemListResponse();
        itemListResponse.set(borrowedItems);
        return ResponseEntity.ok(itemListResponse);
    }
}
