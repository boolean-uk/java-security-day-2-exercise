package com.booleanuk.api.library.controllers;

import com.booleanuk.api.library.models.Borrow;
import com.booleanuk.api.library.models.User;
import com.booleanuk.api.library.payload.response.*;
import com.booleanuk.api.library.repositories.BorrowRepository;
import com.booleanuk.api.library.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;
import com.booleanuk.api.library.models.Item;
import com.booleanuk.api.library.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;


@RestController
@RequestMapping("items")
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private BorrowRepository borrowRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("user/get")
    public ResponseEntity<ItemListResponse> getAllItems() {
        ItemListResponse itemListResponse = new ItemListResponse();
        itemListResponse.set(this.itemRepository.findAll());
        return ResponseEntity.ok(itemListResponse);
    }

    @PostMapping("admin/create")
    public ResponseEntity<Response<?>> createItem(@RequestBody Item item) {
        ItemResponse itemResponse = new ItemResponse();
        try {
            itemResponse.set(this.itemRepository.save(item));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }

    @GetMapping("admin/{id}")
    public ResponseEntity<Response<?>> getItemById(@PathVariable int id) {
        Item item = this.itemRepository.findById(id).orElse(null);
        if (item == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.set(item);
        return ResponseEntity.ok(itemResponse);
    }

    @PutMapping("admin/update/{id}")
    public ResponseEntity<Response<?>> updateItem(@PathVariable int id, @RequestBody Item item) {
        Item itemToUpdate = this.itemRepository.findById(id).orElse(null);
        if (itemToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        itemToUpdate.setName(item.getName());
        itemToUpdate.setItemType(item.getItemType());
        itemToUpdate.setAvailable(item.isAvailable());

        try {
            itemToUpdate = this.itemRepository.save(itemToUpdate);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.set(itemToUpdate);
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("admin/delete/{id}")
    public ResponseEntity<Response<?>> deleteItem(@PathVariable int id) {
        Item itemToDelete = this.itemRepository.findById(id).orElse(null);
        if (itemToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.itemRepository.delete(itemToDelete);
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.set(itemToDelete);
        return ResponseEntity.ok(itemResponse);
    }

    @PutMapping("user/borrow/{iId}/user/{uId}")
    public ResponseEntity<Response<?>> borrow(@PathVariable("iId") int iId, @PathVariable("uId") int uId) {
        Item itemToBorrow = this.itemRepository.findById(iId).orElse(null);
        User userToBorrow = this.userRepository.findById(uId).orElse(null);

        if (itemToBorrow == null || userToBorrow == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        if (itemToBorrow.isAvailable() == false) {
            ErrorResponse error = new ErrorResponse();
            error.set("Item is not available");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        Borrow loan = new Borrow(userToBorrow, itemToBorrow, LocalDate.now());

        itemToBorrow.setAvailable(false);
        try {
            loan = this.borrowRepository.save(loan);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        try {
            itemToBorrow = this.itemRepository.save(itemToBorrow);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.set(itemToBorrow);
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }

    @PutMapping("user/return/{id}")
    public ResponseEntity<Response<?>> turnIn(@PathVariable int id) {
        Borrow loanToReturn = this.borrowRepository.findById(id).orElse(null);

        if (loanToReturn == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Item itemToReturn = this.itemRepository.findById(loanToReturn.getItem().getId()).orElse(null);
        if (itemToReturn.isAvailable() == true) {
            ErrorResponse error = new ErrorResponse();
            error.set("We already have that item");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        loanToReturn.setReturned(LocalDate.now());
        itemToReturn.setAvailable(true);

        try {
            loanToReturn = this.borrowRepository.save(loanToReturn);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        try {
            itemToReturn = this.itemRepository.save(itemToReturn);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.set(itemToReturn);
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }

    @GetMapping("user/getcurrentloans/{id}")
    public ResponseEntity<BorrowListResponse> getCurrentLoans(@PathVariable int id) {
        BorrowListResponse borrowListResponse = new BorrowListResponse();
        ItemListResponse itemListResponse = new ItemListResponse();
        User user = this.userRepository.findById(id).orElse(null);

        borrowListResponse.set(this.borrowRepository.findItemByUserIdAndReturned(user.getId(), null));

        return ResponseEntity.ok(borrowListResponse);
    }

    @GetMapping("user/gethistory/{id}")
    public ResponseEntity<BorrowListResponse> getBorrowHistory(@PathVariable int id) {
        BorrowListResponse borrowListResponse = new BorrowListResponse();
        User user = this.userRepository.findById(id).orElse(null);

        borrowListResponse.set(this.borrowRepository.findItemByUserId(user.getId()));

        return ResponseEntity.ok(borrowListResponse);
    }

    @GetMapping("admin/getuserhistory/{id}")
    public ResponseEntity<BorrowListResponse> adminGetUserBorrowHistory(@PathVariable int id) {
        BorrowListResponse borrowListResponse = new BorrowListResponse();
        User user = this.userRepository.findById(id).orElse(null);

        borrowListResponse.set(this.borrowRepository.findItemByUserId(user.getId()));

        return ResponseEntity.ok(borrowListResponse);
    }

    @GetMapping("admin/getitemhistory/{id}")
    public ResponseEntity<BorrowListResponse> adminGetItemBorrowHistory(@PathVariable int id) {
        BorrowListResponse borrowListResponse = new BorrowListResponse();
        Item item = this.itemRepository.findById(id).orElse(null);

        borrowListResponse.set(this.borrowRepository.findUserByItemId(item.getId()));

        return ResponseEntity.ok(borrowListResponse);
    }
}
