package com.booleanuk.api.library.controllers;

import com.booleanuk.api.library.payload.response.ErrorResponse;
import com.booleanuk.api.library.payload.response.Response;
import com.booleanuk.api.library.payload.response.UserResponse;
import com.booleanuk.api.library.repository.LoanRepository;
import com.booleanuk.api.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanRepository loanRepository;

    private ErrorResponse errorResponse = new ErrorResponse();
    private UserResponse userResponse = new UserResponse();

    /*@GetMapping("/loans")
    public ResponseEntity<Response<?>> getUserLoans(@PathVariable int id) {

    }*/
}
