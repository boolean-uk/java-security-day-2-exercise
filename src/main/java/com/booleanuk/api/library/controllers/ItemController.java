package com.booleanuk.api.library.controllers;

import com.booleanuk.api.library.models.Item;
import com.booleanuk.api.library.payload.response.ItemListResponse;
import com.booleanuk.api.library.payload.response.ItemResponse;
import com.booleanuk.api.library.payload.response.ErrorResponse;
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

    @PostMapping
    public ResponseEntity<Response<?>> createItem(@RequestBody Item book) {
        ItemResponse itemResponse = new ItemResponse();
        try {
            itemResponse.set(this.itemRepository.save(book));
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
    public ResponseEntity<Response<?>> updateItem(@PathVariable int id, @RequestBody Item request) {
        Item item = this.itemRepository.findById(id).orElse(null);
        if (item == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        item.setTitle(request.getTitle());
        item.setObject(request.getObject());
        item.setAvailable(request.getAvailable());

        try {
            item = this.itemRepository.save(item);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.set(item);
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteItem(@PathVariable int id) {
        Item item = this.itemRepository.findById(id).orElse(null);
        if (item == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.itemRepository.delete(item);
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.set(item);
        return ResponseEntity.ok(itemResponse);
    }
}