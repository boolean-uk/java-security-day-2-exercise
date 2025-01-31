package com.booleanuk.api.library.controller;

import com.booleanuk.api.library.model.EItem;
import com.booleanuk.api.library.model.Item;
import com.booleanuk.api.library.model.User;
import com.booleanuk.api.library.payload.response.*;
import com.booleanuk.api.library.repository.ItemRepository;
import com.booleanuk.api.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("items")
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<ItemListResponse> getAllItems() {
        ItemListResponse itemListResponse = new ItemListResponse();
        itemListResponse.set(this.itemRepository.findAll());
        return ResponseEntity.ok(itemListResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<Response<?>> createItem(@RequestBody Item item) {
        ItemResponse itemResponse = new ItemResponse();
        try {

            for(int i = 0; i < EItem.values().length; i++){
                if(EItem.values()[i].toString().equals(item.getType())){
                    itemResponse.set(this.itemRepository.save(item));
                    break;
                }
                if(i == EItem.values().length - 1){
                    ErrorResponse error = new ErrorResponse();
                    error.set("Bad request");
                    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
                }
            }

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
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

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateItem(@PathVariable int id, @RequestBody Item item) {
        Item itemToUpdate = this.itemRepository.findById(id).orElse(null);
        if (itemToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        itemToUpdate.setName(item.getName());
        itemToUpdate.setType(item.getType());
        itemToUpdate.setIsBorrowed(item.getIsBorrowed());

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

    @DeleteMapping("/{id}")
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


    // for simple users
    @PutMapping("/{uId}/borrow/{iId}")
    public ResponseEntity<Response<?>> borrowItem(@PathVariable int uId,@PathVariable int iId) {
        ItemResponse itemResponse = new ItemResponse();
        try {
            Item itemToBorrow = this.itemRepository.findById(iId).orElse(null);
            User userThatBorrows = this.userRepository.findById(uId).orElse(null);
            if(itemToBorrow == null || userThatBorrows == null){
                ErrorResponse error = new ErrorResponse();
                error.set("not found");
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            } else if (itemToBorrow.getIsBorrowed()) {
                ErrorResponse error = new ErrorResponse();
                error.set("Bad request");
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }
            itemToBorrow.setIsBorrowed(true);
            userThatBorrows.getCurrentlyBorrowedItems().add(itemToBorrow);
            userThatBorrows.setCurrentlyBorrowedItems(userThatBorrows.getCurrentlyBorrowedItems());
            this.userRepository.save(userThatBorrows);

            itemResponse.set(this.itemRepository.save(itemToBorrow));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }


    @PutMapping("/{uId}/return/{iId}")
    public ResponseEntity<Response<?>> returnItem(@PathVariable int uId,@PathVariable int iId ) {
        ItemResponse itemResponse = new ItemResponse();
        try {
            Item itemToReturn = this.itemRepository.findById(iId).orElse(null);
            User userThatBorrows = this.userRepository.findById(uId).orElse(null);
            if(itemToReturn == null || userThatBorrows == null){
                ErrorResponse error = new ErrorResponse();
                error.set("not found");
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            } else if (!itemToReturn.getIsBorrowed()) {
                ErrorResponse error = new ErrorResponse();
                error.set("Bad request");
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }
            itemToReturn.setIsBorrowed(false);
            userThatBorrows.getCurrentlyBorrowedItems().remove(itemToReturn);
            userThatBorrows.setCurrentlyBorrowedItems(userThatBorrows.getCurrentlyBorrowedItems());

            userThatBorrows.getHistoricallyBorrowedItems().add(itemToReturn);
            userThatBorrows.setHistoricallyBorrowedItems(userThatBorrows.getHistoricallyBorrowedItems());
            this.userRepository.save(userThatBorrows);

            itemResponse.set(this.itemRepository.save(itemToReturn));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }

    @GetMapping("/current/{id}")
    public ResponseEntity<Response<?>> getCurrentItems(@PathVariable int id) {
        ItemSetResponse itemSetResponse = new ItemSetResponse();
        try {
            User userThatBorrows = this.userRepository.findById(id).orElse(null);
            if(userThatBorrows == null){
                ErrorResponse error = new ErrorResponse();
                error.set("not found");
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            itemSetResponse.set(userThatBorrows.getCurrentlyBorrowedItems());
            return ResponseEntity.ok(itemSetResponse);

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/historic/{id}")
    public ResponseEntity<Response<?>> getHistoricItems(@PathVariable int id) {
        ItemSetResponse itemSetResponse = new ItemSetResponse();
        try {
            User userThatBorrows = this.userRepository.findById(id).orElse(null);
            if(userThatBorrows == null){
                ErrorResponse error = new ErrorResponse();
                error.set("not found");
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }

            itemSetResponse.set(userThatBorrows.getHistoricallyBorrowedItems());
            return ResponseEntity.ok(itemSetResponse);

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

    }
}
