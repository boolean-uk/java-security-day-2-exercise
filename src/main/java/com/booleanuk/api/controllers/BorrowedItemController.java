package com.booleanuk.api.controllers;

import com.booleanuk.api.dto.BorrowedItemDto;
import com.booleanuk.api.models.BorrowedItem;
import com.booleanuk.api.models.Item;
import com.booleanuk.api.models.User;
import com.booleanuk.api.payload.response.BorrowedItemListResponse;
import com.booleanuk.api.payload.response.BorrowedItemResponse;
import com.booleanuk.api.payload.response.ErrorResponse;
import com.booleanuk.api.payload.response.Response;
import com.booleanuk.api.repository.BorrowedItemRepository;
import com.booleanuk.api.repository.ItemRepository;
import com.booleanuk.api.repository.UserRepository;
import com.booleanuk.api.security.jwt.JwtUtils;
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

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping
    public ResponseEntity<Response<?>> getAllCurrent(@RequestHeader (name="Authorization") String token) {
        return this.getAllCurrentForUser(this.getUserIdFromToken(token.substring(7)));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<Response<?>> getAllCurrentForUser(@PathVariable int userId) {
        BorrowedItemListResponse borrowedItemListResponse = new BorrowedItemListResponse();
        borrowedItemListResponse.set(this.repository.findCurrentlyBorrowedItemsByUserId(userId));
        return ResponseEntity.ok(borrowedItemListResponse);
    }


    @GetMapping("/history")
    public ResponseEntity<Response<?>> getAllHistory(@RequestHeader (name="Authorization") String token) {
        return this.getAllHistoryForUser(this.getUserIdFromToken(token.substring(7)));
    }


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


    @PostMapping("/items/{itemId}")
    public ResponseEntity<Response<?>> lendItem(@RequestHeader (name="Authorization") String token, @PathVariable int itemId) {
        return this.lendItemForUser(this.getUserIdFromToken(token.substring(7)), itemId);
    }

    @PostMapping("/users/{userId}/items/{itemId}")
    public ResponseEntity<Response<?>> lendItemForUser(@PathVariable int userId, @PathVariable int itemId) {
        Item item = this.itemRepository.findById(itemId).orElse(null);
        User user = this.userRepository.findById(userId).orElse(null);
        if (item == null || user == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        if (item.getBorrowedItems().stream().anyMatch(x -> x.getReturnDate() == null)) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        BorrowedItem borrowedItem = this.repository.save(new BorrowedItem(user, item));

        BorrowedItemResponse borrowedItemResponse = new BorrowedItemResponse();
        borrowedItemResponse.set(this.translateToDto(borrowedItem));

        return new ResponseEntity<>(borrowedItemResponse, HttpStatus.CREATED);
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<Response<?>> returnItem(@RequestHeader (name="Authorization") String token, @PathVariable int itemId) {
        return this.returnItemForUser(this.getUserIdFromToken(token.substring(7)), itemId);
    }

    @PutMapping("/users/{userId}/items/{itemId}")
    public ResponseEntity<Response<?>> returnItemForUser(@PathVariable int userId, @PathVariable int itemId) {
        List<BorrowedItem> borrowedItems = this.repository.findByUserIdAndItemId(userId, itemId);
        if (borrowedItems.isEmpty()) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        } if (borrowedItems.get(0).getReturnDate() != null) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        BorrowedItem borrowedItem = borrowedItems.get(0);
        borrowedItem.setReturnDate(LocalDate.now());

        BorrowedItemResponse borrowedItemResponse = new BorrowedItemResponse();
        borrowedItemResponse.set(this.translateToDto(this.repository.save(borrowedItem)));

        return new ResponseEntity<>(borrowedItemResponse, HttpStatus.CREATED);
    }

    public int getUserIdFromToken(String token) {
        String username = this.jwtUtils.getUserNameFromJwtToken(token.substring(7));
        User user = this.userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return -1;
        }
        return user.getId();
    }

    public BorrowedItemDto translateToDto(BorrowedItem borrowedItem) {
        return new BorrowedItemDto(borrowedItem.getId(), borrowedItem.getItem().getName(), borrowedItem.getUser().getUsername(), borrowedItem.getBorrowDate(), borrowedItem.getReturnDate());
    }
}
