package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Item;
import com.booleanuk.api.payload.response.ErrorResponse;
import com.booleanuk.api.payload.response.ItemListResponse;
import com.booleanuk.api.payload.response.ItemResponse;
import com.booleanuk.api.payload.response.Response;
import com.booleanuk.api.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("items")
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public ResponseEntity<ItemListResponse> getAllItems() {
        ItemListResponse ItemListResponse = new ItemListResponse();
        ItemListResponse.set(this.itemRepository.findAll());
        return ResponseEntity.ok(ItemListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createItem(@RequestBody Item item) {
        ItemResponse ItemResponse = new ItemResponse();
        try {
            ItemResponse.set(this.itemRepository.save(item));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ItemResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getItemById(@PathVariable int id) {
        Item item = this.itemRepository.findById(id).orElse(null);
        if (item == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        ItemResponse ItemResponse = new ItemResponse();
        ItemResponse.set(item);
        return ResponseEntity.ok(ItemResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateItem(@PathVariable int id, @RequestBody Item item) {
        Item itemToUpdate = this.itemRepository.findById(id).orElse(null);
        if (itemToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Item prevItem = this.itemRepository.findById(id).orElse(null);
        if (prevItem == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        if (item.getItemName() == null) {
            itemToUpdate.setItemName(prevItem.getItemName());
        }
        else {
            itemToUpdate.setItemName(item.getItemName());
        }
        if (item.getItemType() == null) {
            itemToUpdate.setItemType(prevItem.getItemType());
        }
        else {
            itemToUpdate.setItemType(item.getItemType());
        }

        try {
            itemToUpdate = this.itemRepository.save(itemToUpdate);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        ItemResponse ItemResponse = new ItemResponse();
        ItemResponse.set(itemToUpdate);
        return new ResponseEntity<>(ItemResponse, HttpStatus.CREATED);
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
        ItemResponse ItemResponse = new ItemResponse();
        ItemResponse.set(itemToDelete);
        return ResponseEntity.ok(ItemResponse);
    }
}
