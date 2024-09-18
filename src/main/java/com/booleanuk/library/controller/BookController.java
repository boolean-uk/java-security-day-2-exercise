package com.booleanuk.library.controller;

import com.booleanuk.library.model.Book;
import com.booleanuk.library.repository.BookRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController extends GenericController<Book, BookRepository> {

    @Override
    protected void updateItemValues(Book existingBook, Book updatedBook){
        if (updatedBook.getTitle() != null){
            existingBook.setTitle(updatedBook.getTitle());
        }
    }
}