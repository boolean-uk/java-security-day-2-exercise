package com.booleanuk.api.controllers;

import com.booleanuk.api.dto.BorrowedItemDto;
import com.booleanuk.api.models.BorrowedItem;
import com.booleanuk.api.models.User;
import com.booleanuk.api.payload.response.BorrowedItemListResponse;
import com.booleanuk.api.payload.response.ErrorResponse;
import com.booleanuk.api.payload.response.Response;
import com.booleanuk.api.repository.BorrowedItemRepository;
import com.booleanuk.api.repository.ItemRepository;
import com.booleanuk.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("borrow")
public class BorrowedItemController {
    @Autowired
    BorrowedItemRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    //@GetMapping
    //public ResponseEntity<Response<?>> getAllCurrent()

    @GetMapping("/users/{userId}")
    public ResponseEntity<Response<?>> getAllCurrentForUser(@PathVariable int userId) {
        BorrowedItemListResponse borrowedItemListResponse = new BorrowedItemListResponse();
        borrowedItemListResponse.set(this.repository.findCurrentlyBorrowedItemsByUserId(userId));
        return ResponseEntity.ok(borrowedItemListResponse);
    }

    //@GetMapping("/history")


    @GetMapping("/history/users/{userId}")
    public ResponseEntity<Response<?>> getAllHistoryForUser(@PathVariable int userId) {
        BorrowedItemListResponse borrowedItemListResponse = new BorrowedItemListResponse();
        borrowedItemListResponse.set(this.repository.findPreviouslyBorrowedItemsByUserId(userId));
        return ResponseEntity.ok(borrowedItemListResponse);
    }

    @GetMapping("/history/items/{itemId}")
    public ResponseEntity<Response<?>> getAllForItem(@PathVariable int itemId) {
        BorrowedItemListResponse borrowedItemListResponse = new BorrowedItemListResponse();
        borrowedItemListResponse.set(this.repository.findBorrowedItemsByItemId(itemId));
        return ResponseEntity.ok(borrowedItemListResponse);
    }

}
