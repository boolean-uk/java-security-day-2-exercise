package com.booleanuk.api.library.controller;

import com.booleanuk.api.library.model.Item;
import com.booleanuk.api.library.payload.response.ErrorResponse;
import com.booleanuk.api.library.payload.response.ItemListResponse;
import com.booleanuk.api.library.payload.response.ItemResponse;
import com.booleanuk.api.library.payload.response.Response;
import com.booleanuk.api.library.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("items")
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public ResponseEntity<ItemListResponse> getAllItems() {
        ItemListResponse itemListResponse = new ItemListResponse();
        itemListResponse.set(this.itemRepository.findAll());
        return ResponseEntity.ok(itemListResponse);
    }

    @PostMapping("create")
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
    @PostMapping("/borrow/{id}")
    public ResponseEntity<Response<?>> borrowItem(@RequestBody Item item) {
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

    @PostMapping("/return/{id}")
    public ResponseEntity<Response<?>> returnItem(@RequestBody Item item) {
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
}
