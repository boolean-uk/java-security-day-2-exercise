package com.booleanuk.api.controller;


import com.booleanuk.api.model.Item;
import com.booleanuk.api.repository.ItemRepository;
import com.booleanuk.api.response.ErrorResponse;
import com.booleanuk.api.response.ItemListResponse;
import com.booleanuk.api.response.ItemResponse;
import com.booleanuk.api.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public ResponseEntity<ItemListResponse> getAllItems() {
        ItemListResponse itemListResponse = new ItemListResponse();
        itemListResponse.set(this.itemRepository.findAll());
        return ResponseEntity.ok(itemListResponse);
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

    @PostMapping
    public ResponseEntity<Response<?>> createItem(@RequestBody Item item) {
        this.itemRepository.save(item);
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.set(item);
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateItem(@PathVariable int id, @RequestBody Item item) {
        Item itemToUpdate = this.itemRepository.findById(id).orElse(null);
        if (itemToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        itemToUpdate.setYear(item.getYear());
        itemToUpdate.setGenre(item.getGenre());
        itemToUpdate.setPublisher(item.getPublisher());
        itemToUpdate.setTitle(item.getTitle());
        itemToUpdate.setLoans(item.getLoans());

        this.itemRepository.save(itemToUpdate);
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.set(itemToUpdate);
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Response<?>> deleteItem(@PathVariable int id) {
        Item item = this.itemRepository.findById(id).orElse(null);
        if (item == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.set(item);
        this.itemRepository.delete(item);
        return ResponseEntity.ok(itemResponse);
    }
}
