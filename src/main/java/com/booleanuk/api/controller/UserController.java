package com.booleanuk.api.controller;

import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.UserRepository;
import com.booleanuk.payload.response.ErrorResponse;
import com.booleanuk.payload.response.Response;
import com.booleanuk.payload.response.UserListResponse;
import com.booleanuk.payload.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Response<?>> getAllUsers()    {
        UserListResponse userListResponse = new UserListResponse();
        userListResponse.set(
                "Successfully returned a list of all users",
                this.userRepository.findAll()
        );

        return ResponseEntity.ok(userListResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getOneUser(@PathVariable int id) {
        User user = this.userRepository.findById(id).orElse(null);
        if(user == null)
        {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No user with that id were found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        UserResponse userResponse = new UserResponse();
        userResponse.set("Successfully returned user", user);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createUser(@RequestBody User user)   {
        if(user.getName() == null
        || user.getEmail() == null
        || user.getPhone() == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("One or more required fields are null");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        UserResponse userResponse = new UserResponse();
        userResponse.set(
                "Successfully created user",
                this.userRepository.save(user));
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateUser(@PathVariable int id, @RequestBody User user) {
        if(user.getName() == null
                || user.getEmail() == null
                || user.getPhone() == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("One or more required fields are null");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        User userToUpdate = this.userRepository.findById(id).orElse(null);
        if(userToUpdate == null)
        {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No user with that id was found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        userToUpdate.setName(user.getName());
        userToUpdate.setPhone(user.getPhone());
        userToUpdate.setEmail(user.getEmail());

        UserResponse userResponse = new UserResponse();
        userResponse.set("Successfully updated user", userToUpdate);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteUser(@PathVariable int id) {
        User userToDelete = this.userRepository.findById(id).orElse(null);
        if(userToDelete == null)
        {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No user with that id was found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        this.userRepository.delete(userToDelete);
        UserResponse userResponse = new UserResponse();
        userResponse.set(
                "Successfully deleted user",
                userToDelete);

        return ResponseEntity.ok(userResponse);
    }
}
