package com.booleanuk.library.controllers;

import com.booleanuk.library.HelperUtils;
import com.booleanuk.library.models.Book;
import com.booleanuk.library.models.User;
import com.booleanuk.library.payload.response.ApiResponse;
import com.booleanuk.library.payload.response.BookListResponse;
import com.booleanuk.library.repository.BookRepository;
import com.booleanuk.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        return HelperUtils.okRequest(this.userRepository.findAll());
    }

    @PostMapping("/{userId}/borrow-book/{bookId}")
    public ResponseEntity<?> borrowBook(@PathVariable int userId, @PathVariable int bookId) {
        Optional<User> optionalUser = this.userRepository.findById(userId);
        Optional<Book> optionalBook = this.bookRepository.findById(bookId);

        if (optionalUser.isPresent() && optionalBook.isPresent()) {
            User user = optionalUser.get();
            Book book = optionalBook.get();

            // Check if the book is available to borrow
            if (book.getUser() == null) {
                book.setUser(user);
                this.bookRepository.save(book);
                user.getCurrentItems().add(book);
                user.getBorrowedHistory().add(book);
                this.userRepository.save(user);
                return HelperUtils.createdRequest("Book borrowed successfully.");
            } else {
                return HelperUtils.badRequest(new ApiResponse.Message("Book is already borrowed."));
            }
        } else {
            return HelperUtils.badRequest(new ApiResponse.Message("Book or User does not exist."));
        }
    }

    @PostMapping("/{userId}/return-book/{bookId}")
    public ResponseEntity<?> returnBook(@PathVariable int userId, @PathVariable int bookId) {
        Optional<User> optionalUser = this.userRepository.findById(userId);
        Optional<Book> optionalBook = this.bookRepository.findById(bookId);

        if (optionalUser.isPresent() && optionalBook.isPresent()) {
            User user = optionalUser.get();
            Book book = optionalBook.get();

            if (user.getCurrentItems().contains(book)) {
                user.getCurrentItems().remove(book);

                book.setUser(null);
                this.userRepository.save(user);
                this.bookRepository.save(book);

                return HelperUtils.okRequest("Book returned successfully.");
            } else {
                return HelperUtils.badRequest("User has not borrowed this book.");
            }
        } else {
            return HelperUtils.badRequest(new ApiResponse.Message("Book or User does not exist."));
        }
    }

    @GetMapping("/{userId}/current-items")
    public ResponseEntity<?> getCurrentItemsByUserId(@PathVariable int userId) {
        Optional<User> optionalUser = this.userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Book> currentItems = user.getCurrentItems();
            return HelperUtils.okRequest(currentItems);
        } else {
            return HelperUtils.badRequest(new ApiResponse.Message("User does not exist."));
        }
    }

}
