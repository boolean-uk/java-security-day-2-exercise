package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Item;
import com.booleanuk.api.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemRepository items;

    @Autowired
    public ItemController(ItemRepository items) {
        this.items = items;
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        Item newItem = items.save(item);
        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> itemsList = items.findAll();
        return new ResponseEntity<>(itemsList, HttpStatus.OK);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Item> getItemById(@PathVariable int itemId) {
        Item item = items.findById(itemId).orElse(null);
        if (item == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<Item> updateItem(@PathVariable int itemId, @RequestBody Item updatedItem) {
        Item item = items.findById(itemId).orElse(null);
        if (item == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        item.setTitle(updatedItem.getTitle());
        item.setType(updatedItem.getType());
        Item savedItem = items.save(item);
        return new ResponseEntity<>(savedItem, HttpStatus.OK);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable int itemId) {
        if (!items.existsById(itemId)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        items.deleteById(itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
