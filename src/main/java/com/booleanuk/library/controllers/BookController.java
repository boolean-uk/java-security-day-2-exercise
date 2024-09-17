package com.booleanuk.library.controllers;

import com.booleanuk.library.models.Book;
import com.booleanuk.library.repository.BookRepository;
import com.booleanuk.library.payload.response.BookListResponse;
import com.booleanuk.library.payload.response.BookResponse;
import com.booleanuk.library.payload.response.ErrorResponse;
import com.booleanuk.library.payload.response.Response;
import com.booleanuk.library.repository.RoleRepository;
import com.booleanuk.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("books")
public class BookController {
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	AuthenticationManager authenticationManager;


	@GetMapping
	public ResponseEntity<BookListResponse> getAllBooks() {
		BookListResponse bookListResponse = new BookListResponse();
		bookListResponse.set(this.bookRepository.findAll());
		return ResponseEntity.ok(bookListResponse);
	}

	@PostMapping
	public ResponseEntity<Response<?>> createBook(@RequestBody Book book) {

		if (book.getTitle() == null && book.getAuthor() == null && book.getPublisher() == null && book.getYear() == 0 && book.getGenre() == null) {
			ErrorResponse error = new ErrorResponse();
			error.set("bad request");
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
		BookResponse bookResponse = new BookResponse();
		try {
			bookResponse.set(this.bookRepository.save(book));
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.set("Bad request");
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
	}


	@GetMapping("/{id}")
	public ResponseEntity<Response<?>> getBookById(@PathVariable int id) {
		Book book = this.bookRepository.findById(id).orElse(null);
		if (book == null) {
			ErrorResponse error = new ErrorResponse();
			error.set("not found");
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
		BookResponse bookResponse = new BookResponse();
		bookResponse.set(book);
		return ResponseEntity.ok(bookResponse);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Response<?>> updateBook(@PathVariable int id, @RequestBody Book book) {

		Book bookToUpdate = this.bookRepository.findById(id).orElse(null);
		if (bookToUpdate == null) {
			ErrorResponse error = new ErrorResponse();
			error.set("not found");
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
		if (book.getTitle() == null && book.getAuthor() == null && book.getPublisher() == null && book.getYear() == 0 && book.getGenre() == null) {
			ErrorResponse error = new ErrorResponse();
			error.set("bad request");
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}

		if (book.getTitle() != null) {
			bookToUpdate.setTitle(book.getTitle());
		}
		if (book.getAuthor() != null) {
			bookToUpdate.setAuthor(book.getAuthor());
		}
		if (book.getPublisher() != null) {
			bookToUpdate.setPublisher(book.getPublisher());
		}
		if (book.getYear() != 0) {
			bookToUpdate.setYear(book.getYear());
		}
		if (book.getGenre() != null) {
			bookToUpdate.setGenre(book.getGenre());
		}

		try {
			bookToUpdate = this.bookRepository.save(bookToUpdate);
		} catch (Exception e) {
			ErrorResponse error = new ErrorResponse();
			error.set("Bad request");
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
		BookResponse bookResponse = new BookResponse();
		bookResponse.set(bookToUpdate);
		return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response<?>> deleteBook(@PathVariable int id) {

		Book bookToDelete = this.bookRepository.findById(id).orElse(null);
		if (bookToDelete == null) {
			ErrorResponse error = new ErrorResponse();
			error.set("not found");
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
		this.bookRepository.delete(bookToDelete);
		BookResponse bookResponse = new BookResponse();
		bookResponse.set(bookToDelete);
		return ResponseEntity.ok(bookResponse);
	}
}
