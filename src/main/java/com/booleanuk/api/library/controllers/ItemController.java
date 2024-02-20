package com.booleanuk.api.library.controllers;

import com.booleanuk.api.library.models.Item;
import com.booleanuk.api.library.payload.response.ItemListResponse;
import com.booleanuk.api.library.payload.response.ItemResponse;
import com.booleanuk.api.library.payload.response.ErrorResponse;
import com.booleanuk.api.library.payload.response.Response;
import com.booleanuk.api.library.repositories.ItemRepository;
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
        Item Item = this.itemRepository.findById(id).orElse(null);
        if (Item == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.set(Item);
        return ResponseEntity.ok(itemResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateItemById(@PathVariable int id, @RequestBody Item item) {
        Item ItemToUpdate = this.itemRepository.findById(id).orElse(null);
        if (ItemToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        ItemToUpdate.setBook(item.getBook());
        ItemToUpdate.setCDs(item.getCDs());
        ItemToUpdate.setDVDs(item.getDVDs());
        ItemToUpdate.setVideo_games(item.getVideo_games());
        ItemToUpdate.setBoard_games(item.getBoard_games());

        try {
            ItemToUpdate = this.itemRepository.save(ItemToUpdate);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.set(ItemToUpdate);
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteItemById(@PathVariable int id) {
        Item ItemToDelete = this.itemRepository.findById(id).orElse(null);
        if (ItemToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.itemRepository.delete(ItemToDelete);
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.set(ItemToDelete);
        return ResponseEntity.ok(itemResponse);
    }
}
