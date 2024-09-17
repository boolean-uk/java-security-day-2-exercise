package com.booleanuk.library.controllers;


import com.booleanuk.library.Exceptions.BadRequestException;
import com.booleanuk.library.Exceptions.NotFoundException;
import com.booleanuk.library.models.*;
import com.booleanuk.library.payload.response.Response;
import com.booleanuk.library.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("users/{userid}/borrowings")
public class BorrowingController {
    @Autowired
    BorrowingRepository borrowingRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VideoGameRepository videoGameRepository;

    @Autowired
    DVDRepository dvdRepository;

    @Autowired
    CDRepository cdRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BoardGameRepository boardGameRepository;


    @GetMapping()
    public ResponseEntity<?> getAll(@PathVariable int userid) {
      User user =   this.userRepository.findById(userid).orElseThrow(
                () -> new NotFoundException("No movie with that id: " + userid + " found")
        );
        List<Borrowing> borrowings = borrowingRepository.findByUser(user);
        Response<List<Borrowing>> response = new Response<>();
        response.set(borrowings);
        return ResponseEntity.ok(response);
    }

    @PostMapping("videoGames/{videoGameId}")
    public ResponseEntity<?> createVideoGame(@PathVariable int userid, @PathVariable int videoGameId ) {
        User user = this.userRepository.findById(userid).orElseThrow(
                () -> new NotFoundException("No user with that id: " + userid + " found")
        );
        VideoGame videoGame = this.videoGameRepository.findById(videoGameId).orElseThrow(
                () -> new NotFoundException("No video game with that id: " + videoGameId + " found")
        );
        try {
            Borrowing borrowing = new Borrowing();
            borrowing.setUser(user);
            borrowing.setVideoGame(videoGame);
            borrowingRepository.save(borrowing);
            Response<Borrowing> response = new Response<>();
            response.set(borrowing);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }
    }

    @PostMapping("boardGames/{boardGameId}")
    public ResponseEntity<?> createBoardGame(@PathVariable int userid, @PathVariable int boardGameId ) {
        User user = this.userRepository.findById(userid).orElseThrow(
                () -> new NotFoundException("No user with that id: " + userid + " found")
        );
        BoardGame boardGame = this.boardGameRepository.findById(boardGameId).orElseThrow(
                () -> new NotFoundException("No video game with that id: " + boardGameId + " found")
        );
        try {
            Borrowing borrowing = new Borrowing();
            borrowing.setUser(user);
            borrowing.setBoardGame(boardGame);
            borrowingRepository.save(borrowing);
            Response<Borrowing> response = new Response<>();
            response.set(borrowing);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }
    }

    @PostMapping("dvds/{dvdId}")
    public ResponseEntity<?> createDVD(@PathVariable int userid, @PathVariable int dvdId ) {
        User user = this.userRepository.findById(userid).orElseThrow(
                () -> new NotFoundException("No user with that id: " + userid + " found")
        );
        DVD dvd = this.dvdRepository.findById(dvdId).orElseThrow(
                () -> new NotFoundException("No video game with that id: " + dvdId + " found")
        );
        try {
            Borrowing borrowing = new Borrowing();
            borrowing.setUser(user);
            borrowing.setDvd(dvd);
            borrowingRepository.save(borrowing);
            Response<Borrowing> response = new Response<>();
            response.set(borrowing);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }
    }

    @PostMapping("cds/{cdId}")
    public ResponseEntity<?> createCD( @PathVariable int userid, @PathVariable int cdId ) {
        User user = this.userRepository.findById(userid).orElseThrow(
                () -> new NotFoundException("No user with that id: " + userid + " found")
        );
        CD cd = this.cdRepository.findById(cdId).orElseThrow(
                () -> new NotFoundException("No video game with that id: " + cdId + " found")
        );
        try {
            Borrowing borrowing = new Borrowing();
            borrowing.setUser(user);
            borrowing.setCd(cd);
            borrowingRepository.save(borrowing);
            Response<Borrowing> response = new Response<>();
            response.set(borrowing);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }
    }

    @PostMapping("books/{bookId}")
    public ResponseEntity<?> createBook( @PathVariable int userid, @PathVariable int bookId) {
        User user = this.userRepository.findById(userid).orElseThrow(
                () -> new NotFoundException("No user with that id: " + userid + " found")
        );
        Book book = this.bookRepository.findById(bookId).orElseThrow(
                () -> new NotFoundException("No video game with that id: " + bookId + " found")
        );
        try {
            Borrowing borrowing = new Borrowing();
            borrowing.setUser(user);
            borrowing.setBook(book);
            borrowingRepository.save(borrowing);
            Response<Borrowing> response = new Response<>();
            response.set(borrowing);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }

    }
    @PutMapping("{borrowingsId}/videoGames/{videoGameId}")
    public ResponseEntity<?> updateVideoGame(@PathVariable int borrowingsId, @PathVariable int videoGameId, @PathVariable int userid) {
        User user = this.userRepository.findById(userid).orElseThrow(
                () -> new NotFoundException("No user with that id: " + userid + " found")
        );
        VideoGame videoGame = this.videoGameRepository.findById(videoGameId).orElseThrow(
                () -> new NotFoundException("No video game with that id: " + videoGameId + " found")
        );

        Borrowing borrowing = this.borrowingRepository.findById(borrowingsId).orElseThrow(
                () -> new NotFoundException("No video game with that id: " + borrowingsId + " found")
        );

        try {
            borrowing.setUser(user);
            borrowing.setVideoGame(videoGame);
            borrowing.setReturnedAt(OffsetDateTime.now());
            borrowing = this.borrowingRepository.save(borrowing);

        } catch (Exception e) {
            throw new BadRequestException("bad request");
        }
        Response<Borrowing> response = new Response<>();
        response.set(borrowing);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}