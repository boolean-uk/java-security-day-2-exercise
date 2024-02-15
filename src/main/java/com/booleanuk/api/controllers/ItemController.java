package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Item;
import com.booleanuk.api.repositories.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("items")
@AllArgsConstructor
public class ItemController {
    private ItemRepository itemRepository;

    @GetMapping
    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item add(@RequestBody Item item) {
        if (item.haveNullFields()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return itemRepository.save(item);
    }

    @GetMapping("{id}")
    public Item getById(@PathVariable int id) {
        if (itemRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return itemRepository.findById(id).get();
    }

    @PutMapping("{id}")
    public Item update(@PathVariable int id, @RequestBody Item item) {
        if (itemRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Item itemToUpdate = itemRepository.findById(id).get();
        itemToUpdate.setName(item.getName() != null ? item.getName() : itemToUpdate.getName());
        itemToUpdate.setType(item.getType() != null ? item.getType() : itemToUpdate.getType());

        return itemRepository.save(itemToUpdate);
    }

    @DeleteMapping("{id}")
    public Item delete(@PathVariable int id) {
        if (itemRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Item itemToDelete = itemRepository.findById(id).get();
        itemRepository.delete(itemToDelete);
        return itemToDelete;
    }
}
