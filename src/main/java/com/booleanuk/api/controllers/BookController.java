package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Book;
import com.booleanuk.api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return new ResponseEntity<>(this.bookRepository.save(book), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(this.bookRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getOneBook(@PathVariable int id) {
        Book book = this.bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with ID " + id + " not found."));
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book bookDetails) {
        Book bookToUpdate = this.bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with ID " + id + " not found."));

        bookToUpdate.setTitle(bookDetails.getTitle());
        bookToUpdate.setAuthor(bookDetails.getAuthor());
        bookToUpdate.setPublisher(bookDetails.getPublisher());
        bookToUpdate.setGenre(bookDetails.getGenre());
        bookToUpdate.setNumberOfPages(bookDetails.getNumberOfPages());

        return new ResponseEntity<>(this.bookRepository.save(bookToUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable int id) {
        Book bookToDelete = this.bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with ID " + id + " not found."));
        this.bookRepository.delete(bookToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

