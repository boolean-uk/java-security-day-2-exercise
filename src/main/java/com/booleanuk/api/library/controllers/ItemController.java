package com.booleanuk.api.library.controllers;

import com.booleanuk.api.library.models.Item;
import com.booleanuk.api.library.payload.response.Response;
import com.booleanuk.api.library.repository.ItemRepository;
import com.booleanuk.api.library.payload.response.ErrorResponse;
import com.booleanuk.api.library.payload.response.SuccessResponse;import com.booleanuk.api.library.payload.response.SuccessResponse;import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Response<List<Item>>> getAllItems() {
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(itemRepository.findAll()));
    }

    @PostMapping
    public ResponseEntity<Response<?>> createItem(@RequestBody Item item) {
        if(containsNull(item)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("bad request"));
        }
        Response<Item> response = new SuccessResponse<>(itemRepository.save(item));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteItem(@PathVariable int id) {
        Item itemToDelete = findItem(id);
        if(itemToDelete == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("not found"));
        }

        if(!itemToDelete.getLoans().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("bad request"));
        }

        itemRepository.delete(itemToDelete);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(itemToDelete));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateItem(@PathVariable int id, @RequestBody Item item) {
        Item itemToUpdate = findItem(id);
        if(itemToUpdate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("not found"));
        }

        if(item.getName() != null) {
            itemToUpdate.setName(item.getName());
        }
        if(item.getType() != null) {
            itemToUpdate.setType(item.getType());
        }
        
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(itemRepository.save(itemToUpdate)));
    }

    private Item findItem(int id) {
        return itemRepository.findById(id).orElse(null);
    }

    private boolean containsNull(Item item) {
        return item.getName() == null || item.getType() == null;
    }
}


