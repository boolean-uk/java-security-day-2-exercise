package com.booleanuk.library.controller;

import com.booleanuk.library.model.Loan;
import com.booleanuk.library.model.User;
import com.booleanuk.library.model.VideoGame;
import com.booleanuk.library.repository.LoanRepository;
import com.booleanuk.library.repository.UserRepository;
import com.booleanuk.library.repository.VideoGameRepository;
import com.booleanuk.library.response.ErrorResponse;
import com.booleanuk.library.response.LoanListResponse;
import com.booleanuk.library.response.LoanResponse;
import com.booleanuk.library.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class LoanController {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VideoGameRepository videoGameRepository;

    @GetMapping("/loans")
    public ResponseEntity<LoanListResponse> getAllLoans() {
        LoanListResponse loanListResponse = new LoanListResponse();
        List<Loan> allSpecifiedLoans = this.loanRepository.findAll();
        loanListResponse.set(allSpecifiedLoans);
        return ResponseEntity.ok(loanListResponse);
    }

    @PostMapping("/{user_id}/videogames/{video_game_id}/loans")
    public ResponseEntity<Response<?>> createLoan(@PathVariable int user_id,
                                                  @PathVariable int video_game_id,
                                                  @RequestBody Loan loan) {
        ErrorResponse error = new ErrorResponse();
        if (this.doesUserIDNotExist(user_id) || this.doesVideoGameIDNotExist(video_game_id)) {
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        if (loan == null) {
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        User tempUser = getUserByID(user_id);
        loan.setUser(tempUser);
        VideoGame tempGame = getVideoGameByID(video_game_id);
        loan.setVideoGame(tempGame);
        this.loanRepository.save(loan);
        LoanResponse loanResponse = new LoanResponse();
        loanResponse.set(loan);
        return new ResponseEntity<>(loanResponse, HttpStatus.CREATED);
    }

    //--------------------------- Private section---------------------------//

    private boolean doesUserIDNotExist(int id) {
        for (User user : this.userRepository.findAll()) {
            if (user.getId() == id) {
                return false;
            }
        }
        return true;
    }

    private boolean doesVideoGameIDNotExist(int id) {
        for (VideoGame videoGame : this.videoGameRepository.findAll()) {
            if (videoGame.getId() == id) {
                return false;
            }
        }
        return true;
    }

    private User getUserByID(int id) {
        for (User user : this.userRepository.findAll()) {
            if (user.getId() == id) {
                return user;
            }
        }
        return new User();
    }

    private VideoGame getVideoGameByID(int id) {
        for (VideoGame videoGame : this.videoGameRepository.findAll()) {
            if (videoGame.getId() == id) {
                return videoGame;
            }
        }
        return new VideoGame();
    }
}
