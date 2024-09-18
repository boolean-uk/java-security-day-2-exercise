package com.booleanuk.library.controller;

import com.booleanuk.library.repository.LoanRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public abstract class GenericController<I, R extends JpaRepository<I, Integer>> {

    @Autowired
    protected R repository;

    @Autowired
    protected LoanRepository loanRepository;


    @PostMapping
    // @PreAuthorize("hasRole('ADMIN')")
    public I addItem (@Valid @RequestBody I item, BindingResult result){

        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please verify all the fields are correct.");
        }

        return repository.save(item);
    }

    @GetMapping
    // @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<I> getAllItems(){
        return this.repository.findAll();
    }

    @GetMapping("/{id}")
    // @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public I getItemById (@PathVariable (name = "id") int id){

        return this.repository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video game not found."));
    }

    @PutMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    public I updateItem (@PathVariable (name = "id") int id, @RequestBody I updatedItem){

        return this.repository.findById(id).map(i -> {
            updateItemValues(i, updatedItem);
            return repository.save(i);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video game with the provided id does not exist"));
    }

    @DeleteMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    public I deleteItem(@PathVariable (name = "id") int id){
        return this.repository.findById(id).map(item -> {
            this.repository.delete(item);
            return item;
        }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video game with the provided id does not exist"));
    }

    /*
    @GetMapping("{id}/loans")
    public List<Loan> getRentedItems(@PathVariable (name = "id") int id) {
        I item = this.repository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "No video games with the provided ID were found."));

        return this.loanRepository.findLoansByVideoGame(videoGame);
    }
    */


    protected abstract void updateItemValues(I item, I updatedItem);
}
