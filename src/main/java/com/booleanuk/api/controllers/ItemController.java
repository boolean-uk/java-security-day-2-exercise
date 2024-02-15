package com.booleanuk.api.controllers;


import com.booleanuk.api.payload.response.ItemListResponse;

import com.booleanuk.api.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("items")
public class ItemController {
    @Autowired
    ItemRepository repository;

    @GetMapping
    public ResponseEntity<?> getAll() {
        ItemListResponse itemListResponse = new ItemListResponse();
        itemListResponse.set(this.repository.findAll());
        return ResponseEntity.ok(itemListResponse);
    }

}
