package com.booleanuk.library.controller;

import com.booleanuk.library.dto.LoanDTO;
import com.booleanuk.library.model.*;
import com.booleanuk.library.repository.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/loans")
public class LoanController {
    @Autowired
    LoanRepository loanRepository;

    @Autowired
    BoardGameRepository boardGameRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CDRepository cdRepository;

    @Autowired
    DVDRepository dvdRepository;

    @Autowired
    VideoGamesRepository videoGamesRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping()
    public Loan borrow(@Valid @RequestBody LoanDTO loanDTO, BindingResult result) {

        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please make sure all fields are properly entered.");
        }

        int userId = loanDTO.getUserId();
        // int itemId = loanDTO.getItemId();

        User user = this.userRepository
                .findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with provided id does not exist"));

        Item item = getItem(loanDTO);

        Loan savedLoan = this.loanRepository.save(new Loan(item, user));

        // item.setAvailable(false);
        // item.getLoanHistory().add(savedLoan);
        // this.itemRepository.save(videoGame);

        // user.getRentedVideoGames().add(savedLoan);
        // this.userRepository.save(user);

        return savedLoan;
    }

    @PutMapping("/{id}/return")
    public Loan returnLoan(@PathVariable (name = "id") int id){
        Loan loan = this.loanRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found."));

        loan.setReturnDate(LocalDateTime.now());
        this.loanRepository.save(loan);

        /*
        Item item = loan.getItem();
        item.setAvailable(true);
        this.videoGamesRepository.save(item);
        */

        return this.loanRepository.save(loan);
    }

    private Item getItem(LoanDTO loanDTO){
        switch (loanDTO.getItemType()) {
            case ("book"):
                return this.bookRepository
                        .findById(loanDTO.getItemId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with provided id does not exist"));
            case ("board_game"):
                return this.boardGameRepository
                        .findById(loanDTO.getItemId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board game with provided id does not exist"));
            case ("cd"):
                return this.cdRepository
                        .findById(loanDTO.getItemId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cd with provided id does not exist"));
            case ("dvd"):
                return this.dvdRepository
                        .findById(loanDTO.getItemId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dvd with provided id does not exist"));
            case ("video_game"):
                return this.videoGamesRepository
                        .findById(loanDTO.getItemId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video game with provided id does not exist"));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item with provided id not found");
    }
}
