package com.booleanuk.library.controller;

import com.booleanuk.library.model.Loan;
import com.booleanuk.library.repository.LoanRepository;
import com.booleanuk.library.model.User;
import com.booleanuk.library.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    LoanRepository loanRepository;


    @PostMapping
    public User addUser(@Valid @RequestBody User user){
        try {
            return this.userRepository.save(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable (name = "id") int id){

        return this.userRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable (name = "id") int id, @RequestBody User updatedUser){

        return this.userRepository.findById(id).map(user -> {
            if (updatedUser.getUsername() != null)
                user.setUsername(updatedUser.getUsername());

            return this.userRepository.save(user);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with the provided id does not exist"));
    }

    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable (name = "id") int id){
        return this.userRepository.findById(id).map(user -> {
            this.userRepository.delete(user);
            return user;
        }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with the provided id does not exist"));
    }

    @GetMapping("{id}/loans")
    public List<Loan> getRentedItems(@PathVariable (name = "id") int id) {
        User user = this.userRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "No users with the provided ID were found."));

        return this.loanRepository.findByUser(user);
    }
}
