package com.booleanuk.api.controllers;

import com.booleanuk.api.models.BorrowItem;
import com.booleanuk.api.models.Item;
import com.booleanuk.api.models.User;
import com.booleanuk.api.payload.requests.BorrowRequest;
import com.booleanuk.api.repositories.BorrowItemRepository;
import com.booleanuk.api.repositories.ItemRepository;
import com.booleanuk.api.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
public class BorrowItemController {
    private final BorrowItemRepository borrowItemRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @PostMapping("items/borrow")
    @ResponseStatus(HttpStatus.CREATED)
    public BorrowItem borrowItem(@RequestBody BorrowRequest request) {
        if (itemRepository.findById(request.getItemId()).isEmpty() || userRepository.findById(request.getUserId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        BorrowItem borrowedItem = new BorrowItem();
        Item item = itemRepository.findById(request.getItemId()).get();
        if (borrowItemRepository.existsByItemAndIsActiveTrue(item)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "item is not available");
        }
        User user = userRepository.findById(request.getUserId()).get();

        borrowedItem.setUser(user);
        borrowedItem.setItem(item);
        borrowedItem.setActive(true);

        return borrowItemRepository.save(borrowedItem);
    }

    @PostMapping("items/return")
    public BorrowItem returnItem(@RequestBody BorrowRequest request) {
        if (itemRepository.findById(request.getItemId()).isEmpty() || userRepository.findById(request.getUserId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Item item = itemRepository.findById(request.getItemId()).get();
        User user = userRepository.findById(request.getUserId()).get();
        if (borrowItemRepository.findByUserAndItem(user, item).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        BorrowItem borrowedItem = borrowItemRepository.findByUserAndItem(user, item).get();
        borrowedItem.setEndDate(LocalDateTime.now());
        borrowedItem.setActive(false);

        return borrowItemRepository.save(borrowedItem);
    }

    @GetMapping("items/{id}/history")
    public List<BorrowItem> getItemHistory(@PathVariable int id) {
        if (itemRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Item item = itemRepository.findById(id).get();
        return borrowItemRepository.findAllByItem(item).get();
    }

    @GetMapping("users/{id}/history")
    public List<BorrowItem> getUserHistory(@PathVariable int id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        User user = userRepository.findById(id).get();
        return borrowItemRepository.findAllByUser(user).get();
    }
}
