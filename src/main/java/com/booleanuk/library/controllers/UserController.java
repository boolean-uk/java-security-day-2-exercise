package com.booleanuk.library.controllers;

import com.booleanuk.library.models.User;
import com.booleanuk.library.repository.BorrowRepository;
import com.booleanuk.library.repository.UserRepository;
import com.booleanuk.library.payload.response.ErrorResponse;
import com.booleanuk.library.payload.response.Response;
import com.booleanuk.library.payload.response.UserListResponse;
import com.booleanuk.library.payload.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BorrowRepository borrowRepository;

    @GetMapping
    public ResponseEntity<UserListResponse> getAllBooks() {
        UserListResponse userListResponse = new UserListResponse();
        userListResponse.set(this.userRepository.findAll());
        return ResponseEntity.ok(userListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getBookById(@PathVariable int id) {
        User user = this.userRepository.findById(id).orElse(null);
        if (user == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        UserResponse bookResponse = new UserResponse();
        bookResponse.set(user);
        return ResponseEntity.ok(bookResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> updateBook(@RequestBody User user) {
        User user1;
        try {
            user1 = this.userRepository.save(user);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not create user");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        UserResponse bookResponse = new UserResponse();
        bookResponse.set(user1);
        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateBook(@PathVariable int id, @RequestBody User user) {
        User user1 = this.userRepository.findById(id).orElse(null);
        if (user1 == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No user with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        try {
            user1.setUsername(user.getUsername());
            user1.setEmail(user.getEmail());
            user1.setRoles(user.getRoles());
            this.userRepository.save(user1);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not update user, please check all fields are correct.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        UserResponse bookResponse = new UserResponse();
        bookResponse.set(user1);
        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteAuthor(@PathVariable int id) {
        User user1 = this.userRepository.findById(id).orElse(null);
        if (user1 == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No user with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.userRepository.delete(user1);
        UserResponse bookResponse = new UserResponse();
        bookResponse.set(user1);
        return ResponseEntity.ok(bookResponse);
    }
}
