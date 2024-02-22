package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Item;
import com.booleanuk.api.models.LoanedItem;
import com.booleanuk.api.models.User;
import com.booleanuk.api.repository.ItemRepository;
import com.booleanuk.api.repository.LoanedItemRepository;
import com.booleanuk.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanedItemRepository loans;
    private final ItemRepository items;
    private final UserRepository users;


    @Autowired
    public LoanController(LoanedItemRepository loans, ItemRepository items, UserRepository users) {
        this.loans = loans;
        this.items = items;
        this.users = users;

    }

    @PostMapping("/borrow")
    public ResponseEntity<LoanedItem> borrowItem(@RequestBody LoanedItem loanedItem) {
        Optional<User> optionalUser = users.findById(loanedItem.getUser().getId());
        Optional<Item> optionalItem = items.findById(loanedItem.getItem().getId());

        if (optionalUser.isEmpty() || optionalItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();
        Item item = optionalItem.get();

        if (!item.getStatus().equals("available")) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        item.setStatus("borrowed");
        item.setUser(user);

        LoanedItem loanedItem_ = new LoanedItem(user, item);
        loanedItem.setBorrowDate(LocalDateTime.now());

        items.save(item);
        LoanedItem newLoan = loans.save(loanedItem_);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/return")
    public ResponseEntity<LoanedItem> returnItem(@RequestBody LoanedItem returnedItem) {
        Optional<LoanedItem> optionalLoanedItem = loans.findById(returnedItem.getId());

        if (optionalLoanedItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        LoanedItem loanedItem = optionalLoanedItem.get();
        Item item = loanedItem.getItem();

        if (!item.getStatus().equals("borrowed") || item.getUser().getId() != returnedItem.getUser().getId()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        item.setStatus("available");
        item.setUser(null);
        loanedItem.setReturnDate(LocalDateTime.now());

        items.save(item);
        LoanedItem returnedLoan = loans.save(loanedItem);
        return new ResponseEntity<>(returnedLoan, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoanedItem>> getLoansByUser(@PathVariable int userId) {
        List<LoanedItem> userLoans = loans.findByUserId(userId);
        return new ResponseEntity<>(userLoans, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<LoanedItem>> getAllLoans() {
        List<LoanedItem> allLoans = loans.findAll();
        return new ResponseEntity<>(allLoans, HttpStatus.OK);
    }
}
