package com.booleanuk.api.controllers;

import com.booleanuk.api.models.User;
import com.booleanuk.api.repositories.UserRepository;
import com.booleanuk.api.payload.responses.ErrorResponse;
import com.booleanuk.api.payload.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAll(){
        Response<List<User>> response = new Response<>();
        response.set(this.userRepository.findAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getOne(@PathVariable int id){
        User user = findTheUser(id);
        if (user == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        Response<User> response = new Response<>();
        response.set(user);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Response<?>> addUser(@RequestBody User user){
        if (user.getUsername() == null ||
                user.getEmail() == null ||
                user.getPhone() == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        Response<User> response = new Response<>();
        response.set(this.userRepository.save(user));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateUser(@PathVariable int id, @RequestBody User user){
        User updateUser = findTheUser(id);
        if (updateUser == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        if (user.getUsername() == null ||
                user.getEmail() == null ||
                user.getPhone() == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("bad request");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        updateUser.setUsername(user.getUsername());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());

        Response<User> response = new Response<>();
        response.set(this.userRepository.save(updateUser));

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteUser(@PathVariable int id){
        User deleteUser = findTheUser(id);
        if (deleteUser == null){
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        this.userRepository.delete(deleteUser);
        deleteUser.setLoaning(new ArrayList<>());
        deleteUser.setRoles(new HashSet<>());
        Response<User> response = new Response<>();
        response.set(deleteUser);
        return ResponseEntity.ok(response);
    }

    private User findTheUser(int id){
        return this.userRepository.findById(id)
                .orElse(null);
    }
}
