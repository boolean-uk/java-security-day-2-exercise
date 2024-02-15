package com.booleanuk.library.controllers;

import com.booleanuk.library.models.Item;
import com.booleanuk.library.repository.ItemRepository;
import com.booleanuk.library.payload.response.ErrorResponse;
import com.booleanuk.library.payload.response.ItemListResponse;
import com.booleanuk.library.payload.response.ItemResponse;
import com.booleanuk.library.payload.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("items")
public class ItemController {
    @Autowired
    ItemRepository itemRepository;

    @GetMapping
    public ResponseEntity<ItemListResponse> getAllBooks() {
        ItemListResponse itemListResponse = new ItemListResponse();
        itemListResponse.set(this.itemRepository.findAll());
        return ResponseEntity.ok(itemListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getBookById(@PathVariable int id) {
        Item item = this.itemRepository.findById(id).orElse(null);
        if (item == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        ItemResponse bookResponse = new ItemResponse();
        bookResponse.set(item);
        return ResponseEntity.ok(bookResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> updateBook(@RequestBody Item item) {
        Item item1;
        try {
            item1 = this.itemRepository.save(item);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not create item");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        ItemResponse bookResponse = new ItemResponse();
        bookResponse.set(item1);
        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateBook(@PathVariable int id, @RequestBody Item item) {
        Item item1 = this.itemRepository.findById(id).orElse(null);
        if (item1 == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No item with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        try {
            item1.setTitle(item.getTitle());
            item1.setGenre(item.getGenre());
            item1.setType(item.getType());
            item1.setIsBorrowed(item.getIsBorrowed());
            this.itemRepository.save(item1);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not update item, please check all fields are correct.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        ItemResponse bookResponse = new ItemResponse();
        bookResponse.set(item1);
        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteAuthor(@PathVariable int id) {
        Item item1 = this.itemRepository.findById(id).orElse(null);
        if (item1 == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No item with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.itemRepository.delete(item1);
        ItemResponse bookResponse = new ItemResponse();
        bookResponse.set(item1);
        return ResponseEntity.ok(bookResponse);
    }
}
