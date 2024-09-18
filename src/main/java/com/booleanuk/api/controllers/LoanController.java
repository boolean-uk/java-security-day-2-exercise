package com.booleanuk.api.controllers;

import com.booleanuk.api.models.*;
import com.booleanuk.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("users/{userId}")
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardGameRepository boardGameRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CDRepository cdRepository;
    @Autowired
    private DVDRepository dvdRepository;
    @Autowired
    private VideoGameRepository videoGameRepository;

    @PostMapping("/boardGames/{boardGameId}")
    public ResponseEntity<Loan> createBoardGameLoan(@RequestBody Loan loan, @PathVariable int userId, @PathVariable int boardGameId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user with that id")
        );
        BoardGame boardGame = this.boardGameRepository.findById(boardGameId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No board game with that id")
        );
        loan.setUser(user);
        loan.setBoardGame(boardGame);
        return new ResponseEntity<>(this.loanRepository.save(loan), HttpStatus.CREATED);
    }

    @PostMapping("/books/{bookId}")
    public ResponseEntity<Loan> createBookLoan(@RequestBody Loan loan, @PathVariable int userId, @PathVariable int bookId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user with that id")
        );
        Book book = this.bookRepository.findById(bookId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No book with that id")
        );
        loan.setUser(user);
        loan.setBook(book);
        return new ResponseEntity<>(this.loanRepository.save(loan), HttpStatus.CREATED);
    }

    @PostMapping("/cds/{cdId}")
    public ResponseEntity<Loan> createCDLoan(@RequestBody Loan loan, @PathVariable int userId, @PathVariable int cdId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user with that id")
        );
        CD cd = this.cdRepository.findById(cdId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No CD with that id")
        );
        loan.setUser(user);
        loan.setCd(cd);
        return new ResponseEntity<>(this.loanRepository.save(loan), HttpStatus.CREATED);
    }

    @PostMapping("/dvds/{dvdId}")
    public ResponseEntity<Loan> createDVDLoan(@RequestBody Loan loan, @PathVariable int userId, @PathVariable int dvdId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user with that id")
        );
        DVD dvd = this.dvdRepository.findById(dvdId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No DVD with that id")
        );
        loan.setUser(user);
        loan.setDvd(dvd);
        return new ResponseEntity<>(this.loanRepository.save(loan), HttpStatus.CREATED);
    }

    @PostMapping("/videoGames/{videoGameId}")
    public ResponseEntity<Loan> createVideoGameLoan(@RequestBody Loan loan, @PathVariable int userId, @PathVariable int videoGameId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user with that id")
        );
        VideoGame videoGame = this.videoGameRepository.findById(videoGameId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No video game with that id")
        );
        loan.setUser(user);
        loan.setVideoGame(videoGame);
        return new ResponseEntity<>(this.loanRepository.save(loan), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Loan> getAllByUserAndGameId(@PathVariable int userId, @PathVariable int gameId) {
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No user with that id")
        );
        BoardGame boardGame = this.boardGameRepository.findById(gameId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No board game with that id")
        );
        return this.loanRepository.findAll();
    }
}
