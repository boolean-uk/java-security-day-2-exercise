package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Item;
import com.booleanuk.api.repositories.ItemRepository;
import com.booleanuk.api.payload.responses.ErrorResponse;
import com.booleanuk.api.payload.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("items")
public class ItemController {
    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAll(){
        Response<List<Item>> response = new Response<>();
        response.set(this.itemRepository.findAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getOne(@PathVariable int id){
        Item item = findTheItem(id);
        if (item == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        Response<Item> response = new Response<>();
        response.set(item);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<?>> addItem(@RequestBody Item item){
        if (item.getTitle() == null ||
        item.getGenre() == null ||
        item.getType() == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        Response<Item> response = new Response<>();
        response.set(this.itemRepository.save(item));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateItem(@PathVariable int id, @RequestBody Item item){
        Item updateItem = findTheItem(id);
        if (updateItem == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("not found");
           return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        if (item.getTitle() == null ||
                item.getGenre() == null ||
                item.getType() == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        updateItem.setTitle(item.getTitle());
        updateItem.setGenre(item.getGenre());
        updateItem.setType(item.getType());
        updateItem.setDescription(item.getDescription());

        Response<Item> response = new Response<>();
        response.set(this.itemRepository.save(updateItem));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteItem(@PathVariable int id){
        Item deleteItem = findTheItem(id);
        if (deleteItem == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        this.itemRepository.delete(deleteItem);
        Response<Item> response = new Response<>();
        response.set(deleteItem);
        return ResponseEntity.ok(response);
    }

    private Item findTheItem(int id){
        return this.itemRepository.findById(id)
                .orElse(null);
    }
}
