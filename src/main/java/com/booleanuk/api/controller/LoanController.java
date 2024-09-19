package com.booleanuk.api.controller;

import com.booleanuk.api.repository.LoanItemRepository;
import com.booleanuk.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private final UserRepository repository;

    @Autowired
    private LoanItemRepository loanItemRepository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

}
