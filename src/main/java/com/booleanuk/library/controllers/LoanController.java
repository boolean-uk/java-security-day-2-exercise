package com.booleanuk.library.controllers;



import com.booleanuk.library.models.Item;
import com.booleanuk.library.models.Loan;
import com.booleanuk.library.models.User;
import com.booleanuk.library.payload.response.*;
import com.booleanuk.library.repository.ItemRepository;
import com.booleanuk.library.repository.LoanRepository;
import com.booleanuk.library.repository.UserRepository;
import com.booleanuk.library.util.DateCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("loans")
public class LoanController {


    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;


    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> loans = this.loanRepository.findAll();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }


    @GetMapping("item/{id}/history")
    public ResponseEntity<Response<?>> getItemLoans(@PathVariable (name = "id") int id) {
        Item item = this.itemRepository.findById(id).orElse(null);

        if(item == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

        }

        if(item.getLoansHistory() == null) {
            item.setLoansHistory(new ArrayList<>());
        }
        ItemResponse itemResponse = new ItemResponse();
        itemResponse.set(item);

        return ResponseEntity.ok(itemResponse);


    }


    @GetMapping("user/{id}/history")
    public ResponseEntity<Response<?>> getUserLoans(@PathVariable (name = "id") int id) {
        User user = this.userRepository.findById(id).orElse(null);

        if(user == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }


        if(user.getLoansHistory() == null) {
            user.setLoansHistory(new ArrayList<>());
        }

        LoanListResponse loanListResponse = new LoanListResponse();
        loanListResponse.set(user.getLoansHistory());
        return new ResponseEntity<>(loanListResponse, HttpStatus.OK);
    }


    @PostMapping("user/{id}/item/{itemId}")
    public ResponseEntity<Response<?>> createLoan(@PathVariable (name = "id") int id,
                                                  @PathVariable (name = "itemId") int itemId,
                                                  @RequestBody Loan loan) {

        User user = this.userRepository.findById(id).orElse(null);
        Item item = this.itemRepository.findById(itemId).orElse(null);

        if(user == null || item == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }


        Loan newLoan = new Loan(loan.getTitle(), DateCreater.getCurrentDate(), DateCreater.getCurrentDate(), user, item);

        item.getLoans().add(newLoan);
        item.getLoansHistory().add(newLoan);
        user.getLoansHistory().add(newLoan);
        user.getLoans().add(newLoan);

        this.loanRepository.save(newLoan);

        LoanResponse loanResponse = new LoanResponse();
        loanResponse.set(newLoan);


        return new ResponseEntity<>(loanResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteLoan(@PathVariable (name = "id") int id) {
        Loan loan = this.loanRepository.findById(id).orElse(null);

        if(loan == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        loan.getItem().getLoans().remove(loan);
        loan.getUser().getLoans().remove(loan);

        this.loanRepository.delete(loan);

        LoanResponse loanResponse = new LoanResponse();
        loanResponse.set(loan);

        return new ResponseEntity<>(loanResponse, HttpStatus.OK);
    }




}
