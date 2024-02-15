package com.booleanuk.api.library.controllers;

import com.booleanuk.api.library.models.Item;
import com.booleanuk.api.library.models.Loan;
import com.booleanuk.api.library.models.User;
import com.booleanuk.api.library.payload.response.Response;
import com.booleanuk.api.library.repository.ItemRepository;
import com.booleanuk.api.library.repository.LoanRepository;
import com.booleanuk.api.library.repository.UserRepository;
import com.booleanuk.api.library.payload.response.ErrorResponse;
import com.booleanuk.api.library.payload.response.SuccessResponse;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/users/{userId}/items/{itemId}")
    public ResponseEntity<Response<?>> getAllLoans(@PathVariable int userId, @PathVariable int itemId) {
        User tempUser = userRepository.findById(userId).orElse(null);
        Item tempItem = itemRepository.findById(itemId).orElse(null);

        if(tempUser == null || tempItem == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("not found"));
        }

        //Find all loans that have the given item and user id
        List<Loan> loans = loanRepository.findAll().stream()
                .filter(loan ->
                        loan.getItem().getId() == itemId && loan.getUser().getId() == userId)
                .toList();


        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(loans));
    }

    @GetMapping("/users/{userId}/loans")
    public ResponseEntity<Response<?>> getAllUserLoans(@PathVariable int userId) {
        User user = userRepository.findById(userId).orElse(null);

        if(user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("not found"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(user.getLoans()));
    }

    @GetMapping("/items/{itemId}/loans")
    public ResponseEntity<Response<?>> getAllItemLoans(@PathVariable int itemId) {
        Item item = itemRepository.findById(itemId).orElse(null);

        if(item == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("not found"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(item.getLoans()));
    }

    @PostMapping("/users/{userId}/items/{itemId}")
    public ResponseEntity<Response<?>> createLoan(@PathVariable int itemId, @PathVariable int userId, @RequestBody Loan loan) {
        User tempUser = userRepository.findById(userId).orElse(null);
        Item tempItem = itemRepository.findById(itemId).orElse(null);

        if(tempUser == null || tempItem == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("not found"));
        }

        loan.setItem(tempItem);
        loan.setUser(tempUser);

        if(loan.getActive() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("bad request"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(loanRepository.save(loan)));
    }

    @PutMapping("/loans/{id}")
    public ResponseEntity<Response<?>> updateLoan(@PathVariable int id, @RequestBody Loan loan) {
        Loan loanToUpdate = loanRepository.findById(id).orElse(null);

        if(loanToUpdate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("not found"));
        }

        if(loan.getActive() == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("bad request"));
        }

        loanToUpdate.setActive(loan.getActive());
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(loanRepository.save(loanToUpdate)));

    }

}
