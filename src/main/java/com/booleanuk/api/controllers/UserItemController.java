package com.booleanuk.api.controllers;

import com.booleanuk.api.models.UserItem;
import com.booleanuk.api.repository.UserItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user-items")
public class UserItemController {

    @Autowired
    private UserItemRepository userItemRepository;

    @GetMapping
    public ResponseEntity<List<UserItem>> getAllUserItems() {
        List<UserItem> userItems = userItemRepository.findAll();
        return new ResponseEntity<>(userItems, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserItem> getUserItemById(@PathVariable int id) {
        Optional<UserItem> userItemOptional = userItemRepository.findById(id);
        return userItemOptional.map(userItem -> new ResponseEntity<>(userItem, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<UserItem> createUserItem(@RequestBody UserItem userItem) {
        UserItem createdUserItem = userItemRepository.save(userItem);
        return new ResponseEntity<>(createdUserItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserItem> updateUserItem(@PathVariable int id, @RequestBody UserItem updatedUserItem) {
        Optional<UserItem> userItemOptional = userItemRepository.findById(id);
        if (userItemOptional.isPresent()) {
            UserItem existingUserItem = userItemOptional.get();
            existingUserItem.setUser(updatedUserItem.getUser());
            existingUserItem.setItem(updatedUserItem.getItem());
            existingUserItem.setBorrowedAt(updatedUserItem.getBorrowedAt());
            existingUserItem.setReturnedAt(updatedUserItem.getReturnedAt());
            existingUserItem.setOngoing(updatedUserItem.isOngoing());

            UserItem savedUserItem = userItemRepository.save(existingUserItem);
            return new ResponseEntity<>(savedUserItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserItem(@PathVariable int id) {
        if (userItemRepository.existsById(id)) {
            userItemRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
