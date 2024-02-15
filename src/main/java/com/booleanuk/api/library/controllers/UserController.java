package com.booleanuk.api.library.controllers;

import com.booleanuk.api.library.models.User;
import com.booleanuk.api.library.payload.response.*;
import com.booleanuk.api.library.repository.BorrowRepository;
import com.booleanuk.api.library.repository.UserRepository;
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
    public ResponseEntity<UserListResponse> getAllItems() {
        UserListResponse userListResponse = new UserListResponse();
        userListResponse.set(this.userRepository.findAll());
        return ResponseEntity.ok(userListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody User user){
        User userCreate;
        try {
            userCreate = this.userRepository.save(user);
        } catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found user");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        UserResponse userResponse = new UserResponse();
        userResponse.set(userCreate);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getUserById(@PathVariable int id){
        User user = this.userRepository.findById(id).orElse(null);
        if (user == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found user");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        UserResponse userResponse = new UserResponse();
        userResponse.set(user);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody User user){
        User user1 = this.userRepository.findById(id).orElse(null);
        if (user1 == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found user");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } try {
            user1.setUsername(user.getUsername());
            user1.setEmail(user.getEmail());
            user1.setRoles(user.getRoles());
            this.userRepository.save(user1);
        } catch (Exception e){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not update user");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        UserResponse userResponse = new UserResponse();
        userResponse.set(user1);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id){
        User user = this.userRepository.findById(id).orElse(null);
        if(user == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Not found user");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        this.userRepository.delete(user);
        UserResponse userResponse = new UserResponse();
        userResponse.set(user);
        return ResponseEntity.ok(userResponse);
    }
}
