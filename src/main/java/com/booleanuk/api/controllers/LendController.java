package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Item;
import com.booleanuk.api.models.Lend;
import com.booleanuk.api.models.User;
import com.booleanuk.api.repositories.ItemRepository;
import com.booleanuk.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("lends")
public class LendController extends ControllerTemplate<Lend> {
    protected record BorrowPost(Integer id, Integer userId, Integer itemId) {}

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;


    @PostMapping("borrow")
    public ResponseEntity<Lend> borrowItem(@RequestBody final BorrowPost request) {
        User _user = userRepository.findById(request.userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Item _item = itemRepository.findById(request.itemId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return new ResponseEntity<>(repository.save(new Lend(_user, _item)), HttpStatus.CREATED);
    }

    @PostMapping("return")
    public ResponseEntity<Lend> returnItem(@RequestBody final BorrowPost request) {
        Lend _lend = repository.findById(request.id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));



        User _user = userRepository.findById(request.userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Item _item = itemRepository.findById(request.itemId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        _item.setIsLent(false);

        return new ResponseEntity<>(_lend, HttpStatus.OK);
    }
}
