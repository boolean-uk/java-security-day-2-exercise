package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Lend;
import com.booleanuk.api.models.User;
import com.booleanuk.api.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController extends ControllerTemplate<User> {
    // i know that doing it like this.. any user has access to view any other people's history
    // but who knows... maybe that's the intended functionality
    @GetMapping("{username}/history")
    public ResponseEntity<List<Lend>> getLendingHistory(@PathVariable final String username) {
        UserRepository _rep = (UserRepository) repository;
        User _user = _rep.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return new ResponseEntity<>(_user.getHistory(), HttpStatus.OK);
    }
}
