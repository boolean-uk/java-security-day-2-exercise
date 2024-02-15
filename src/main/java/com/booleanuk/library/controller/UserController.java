package com.booleanuk.library.controller;

import com.booleanuk.library.model.Loan;
import com.booleanuk.library.model.User;
import com.booleanuk.library.repository.LoanRepository;
import com.booleanuk.library.repository.UserRepository;
import com.booleanuk.library.response.ErrorResponse;
import com.booleanuk.library.response.Response;
import com.booleanuk.library.response.UserListResponse;
import com.booleanuk.library.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LoanRepository loanRepository;

    @GetMapping
    public ResponseEntity<UserListResponse> getAllUsers() {
        UserListResponse userListResponse = new UserListResponse();
        userListResponse.set(this.userRepository.findAll());
        return ResponseEntity.ok(userListResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getUserById(@PathVariable int id) {
        User user = this.userRepository.findById(id).orElse(null);
        if (user == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        UserResponse userResponse = new UserResponse();
        userResponse.set(user);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createUser(@RequestBody User user) {
        if (this.areAnyFieldsBad(user)) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        this.userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.set(user);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteUser(@PathVariable int id) {
        if (this.doesUserIDNotExist(id)) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        User userToDelete = this.getUserByID(id);
        if (!userToDelete.getLoanedGames().isEmpty()) {
            this.removeRelatedLoans(userToDelete);
        }
        this.userRepository.delete(userToDelete);
        UserResponse userResponse = new UserResponse();
        userResponse.set(userToDelete);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateUser(@PathVariable int id, @RequestBody User user) {
        ErrorResponse error = new ErrorResponse();
        if (user == null || (user.getName() == null && user.getEmail() == null && user.getPhone() == null)) {
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        if (this.doesUserIDNotExist(id)) {
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        User userToUpdate = this.getUserByID(id);
        if (user.getName() != null && !user.getName().isBlank()) {
            userToUpdate.setName(user.getName());
        }
        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            userToUpdate.setEmail(user.getEmail());
        }
        if (user.getPhone() != null && !user.getPhone().isBlank()) {
            userToUpdate.setPhone(user.getPhone());
        }
        this.userRepository.save(userToUpdate);
        UserResponse userResponse = new UserResponse();
        userResponse.set(userToUpdate);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }


    private void removeRelatedLoans(User user) {
        for (Loan loan : user.getLoanedGames()) {
            this.loanRepository.delete(loan);
        }
    }

    private boolean doesUserIDNotExist(int id) {
        for (User user : this.userRepository.findAll()) {
            if (user.getId() == id) {
                return false;
            }
        }
        return true;
    }

    private User getUserByID(int id) {
        for (User user : this.userRepository.findAll()) {
            if (user.getId() == id) {
                return user;
            }
        }
        return new User();
    }

    private boolean areAnyFieldsBad(User user) {
        if (user.getName() == null ||
                user.getEmail() == null ||
                user.getPhone() == null ||
                user.getName().isBlank() ||
                user.getEmail().isBlank() ||
                user.getPhone().isBlank())
        {
            return true;
        }
        return false;
    }
}
