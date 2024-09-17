package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Book;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController extends GenericController<Book, Integer> {
}
