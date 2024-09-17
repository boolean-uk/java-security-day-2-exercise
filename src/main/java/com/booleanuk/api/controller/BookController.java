package com.booleanuk.api.controller;

import com.booleanuk.api.model.Book;
import com.booleanuk.api.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("books")
public class BookController {

    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository repository) {
        this.bookRepository = repository;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<Book>> getAll() {
        List<Book> books = this.bookRepository.findAll();
        return ResponseEntity.ok(books);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> create(@RequestBody Book body) {
        Book book = this.bookRepository.save(body);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> update(@PathVariable int id, @RequestBody Book body) {
        Book book = this.bookRepository.findById(id).orElse(null);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        else {
            book.setTitle(body.getTitle());
            book.setGenre(body.getGenre());
            book.setAuthor(body.getAuthor());
            book.setPublisher(body.getPublisher());
            book.setPublishedYear(body.getPublishedYear());
            this.bookRepository.save(book);
        }
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> delete(@PathVariable int id) {
        Book book = this.bookRepository.findById(id).orElse(null);
        if (book == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
        } else {
            this.bookRepository.delete(book);
        }
        return ResponseEntity.ok(book);
    }
}
