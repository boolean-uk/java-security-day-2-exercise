package com.booleanuk.api.library.controllers;

import com.booleanuk.api.library.models.Item;
import com.booleanuk.api.library.models.Loan;
import com.booleanuk.api.library.models.User;
import com.booleanuk.api.library.payload.response.ErrorResponse;
import com.booleanuk.api.library.payload.response.ItemListResponse;
import com.booleanuk.api.library.payload.response.ItemResponse;
import com.booleanuk.api.library.payload.response.Response;
import com.booleanuk.api.library.repository.ItemRepository;
import com.booleanuk.api.library.repository.LoanRepository;
import com.booleanuk.api.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("items")
public class ItemController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private LoanRepository loanRepository;

    @GetMapping
    public ResponseEntity<ItemListResponse> getAllItems() {
        ItemListResponse itemListResponse = new ItemListResponse();
        itemListResponse.set(this.itemRepository.findAll());
        return ResponseEntity.ok(itemListResponse);
    }


    @PostMapping
    public ResponseEntity<Response<?>> createItem(@RequestBody Item item) {
        ItemResponse itemResponse = new ItemResponse();
        try {
            itemResponse.set(this.itemRepository.save(item));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getItemById(@PathVariable int id) {
        Item item = this.itemRepository.findById(id).orElse(null);
        if (item == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.set(item);
        return ResponseEntity.ok(itemResponse);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateItem(@PathVariable int id, @RequestBody Item item) {
        Item itemToUpdate = this.itemRepository.findById(id).orElse(null);
        if (itemToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        itemToUpdate.setTitle(item.getTitle());
        itemToUpdate.setType(item.getType());

        try {
            itemToUpdate = this.itemRepository.save(itemToUpdate);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.set(itemToUpdate);
        return new ResponseEntity<>(itemResponse, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteItem(@PathVariable int id) {
        Item itemToDelete = this.itemRepository.findById(id).orElse(null);
        if (itemToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.itemRepository.delete(itemToDelete);
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.set(itemToDelete);
        return ResponseEntity.ok(itemResponse);
    }

    //Borrow

    @GetMapping("/borrow/{id}")
    public ResponseEntity<?> borrowItem(@PathVariable int id, @AuthenticationPrincipal UserDetails userDetails) {

        Optional<User> userOptional = userRepository.findByUsername(userDetails.getUsername());
        User user = userOptional.orElseThrow(() -> new NoSuchElementException("User not found"));

        Item item = this.getAnItem(id);
        if (item == null) {
            return this.notFound();
        }

        if (item.isBorrowed()) {
            ErrorResponse error = new ErrorResponse();
            error.set("Item is already borrowed");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }


        user.getCurrentlyBorrowedItems().add(item);
        item.setBorrowed(true);
        item.setBorrowedBy(user);


        Loan loan = new Loan(user, item);
        loan.setBorrowedAt(LocalDateTime.now());
        this.loanRepository.save(loan);

        this.userRepository.save(user);
        this.itemRepository.save(item);

        ItemResponse itemResponse = new ItemResponse();
        itemResponse.set(item);
        return ResponseEntity.ok(itemResponse);
    }

    //Return

    @GetMapping("/return/{id}")
    public ResponseEntity<?> returnItem(@PathVariable int id, @AuthenticationPrincipal UserDetails userDetails) {

        Optional<User> userOptional = userRepository.findByUsername(userDetails.getUsername());
        User user = userOptional.orElseThrow(() -> new NoSuchElementException("User not found"));

        Item item = this.getAnItem(id);
        if (item == null) {
            return this.notFound();
        }

        if (item.getBorrowedBy() != user) {
            ErrorResponse error = new ErrorResponse();
            error.set("Game is not borrowed by this user");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }


        user.getCurrentlyBorrowedItems().remove(item);
        item.setBorrowed(false);
        item.setBorrowedBy(null);

        for(Loan loan : this.loanRepository.findAll()){
            if(loan.getUser() == user && loan.getItem() == item && loan.getReturnedAt() == null){
                loan.setReturnedAt(LocalDateTime.now());
            }
        }

        this.userRepository.save(user);
        this.itemRepository.save(item);

        ItemResponse itemResponse = new ItemResponse();
        itemResponse.set(item);
        return ResponseEntity.ok(itemResponse);
    }

    private Item getAnItem(int id){
        return this.itemRepository.findById(id).orElse(null);
    }

    private ResponseEntity<Response<?>> badRequest(){
        ErrorResponse error = new ErrorResponse();
        error.set("Could not create User, please check all required fields are correct");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Response<?>> notFound(){
        ErrorResponse error = new ErrorResponse();
        error.set("No User with that id were found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
