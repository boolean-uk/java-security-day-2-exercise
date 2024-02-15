package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Item;
import com.booleanuk.api.payload.response.ErrorResponse;
import com.booleanuk.api.payload.response.ItemListResponse;
import com.booleanuk.api.payload.response.ItemResponse;
import com.booleanuk.api.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("items")
public class ItemController {
    @Autowired
    ItemRepository repository;

    @GetMapping
    public ResponseEntity<?> getAll() {
        ItemListResponse itemListResponse = new ItemListResponse();
        itemListResponse.set(this.repository.findAll());
        return ResponseEntity.ok(itemListResponse);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Item item) {
        // Get 401 Unauthorized when type dont match an enum, should be fixed, but no time
        ItemResponse itemResponse = new ItemResponse();
        try {
            itemResponse.set(this.repository.save(item));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        //createdItem.setBorrowedItems(new ArrayList<>());

        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> delete(@PathVariable int id) {
        Item itemToDelete = this.repository.findById(id).orElse(null);
        if (itemToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.repository.delete(itemToDelete);
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.set(itemToDelete);
        //Item.setBorrowedItems(new ArrayList<>());
        return ResponseEntity.ok(itemResponse);
    }

}
