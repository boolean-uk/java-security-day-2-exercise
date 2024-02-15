package com.booleanuk.library.controllers;

import com.booleanuk.library.HelperUtils;
import com.booleanuk.library.models.User;
import com.booleanuk.library.payload.response.ApiResponse;
import com.booleanuk.library.payload.response.BookListResponse;
import com.booleanuk.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        return HelperUtils.okRequest(this.userRepository.findAll());
    }


}
