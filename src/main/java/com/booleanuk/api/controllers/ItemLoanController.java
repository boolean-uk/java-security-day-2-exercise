package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Item;
import com.booleanuk.api.models.ItemLoan;
import com.booleanuk.api.models.User;
import com.booleanuk.api.payload.response.CustomResponse;
import com.booleanuk.api.repository.ItemLoanRepository;
import com.booleanuk.api.repository.ItemRepository;
import com.booleanuk.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/itemLoans")
public class ItemLoanController {

    @Autowired
    private ItemLoanRepository itemLoanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/users/{id}")
    public ResponseEntity<CustomResponse> getItemLoansByUserId(@PathVariable int id) {
        if(!userRepository.existsById(id)) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("not found")), HttpStatus.NOT_FOUND);
        }

        User user = userRepository
                .findById(id).get();

        CustomResponse customResponse = new CustomResponse("success", user.getLoans());
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<CustomResponse> getItemLoansByItemId(@PathVariable int id) {
        if(!itemRepository.existsById(id)) {
            return new ResponseEntity<>(new CustomResponse("error", new Error("not found")), HttpStatus.NOT_FOUND);
        }

        Item item = itemRepository
                .findById(id).get();

        CustomResponse customResponse = new CustomResponse("success", item.getLoans());
        return new ResponseEntity<>(customResponse, HttpStatus.OK);
    }

    @PostMapping("/{userId}/items/{itemId}")
    public ResponseEntity<CustomResponse> createItemLoan(@PathVariable int userId, @PathVariable int itemId) {
        if(!userRepository.existsById(userId) || !itemRepository.existsById(itemId)) {
            return new ResponseEntity<>(new CustomResponse("error", "not found"), HttpStatus.NOT_FOUND);
        }

        User user = userRepository
                .findById(userId).get();
        Item item = itemRepository
                .findById(itemId).get();

        ItemLoan itemLoan = new ItemLoan();
        itemLoan.setUser(user);
        itemLoan.setItem(item);
        itemLoanRepository.save(itemLoan);

        return new ResponseEntity<>(new CustomResponse("success", itemLoanRepository.findById(itemLoan.getId())), HttpStatus.OK);
    }
}
