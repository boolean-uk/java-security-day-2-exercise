package com.booleanuk.library.controllers;

import com.booleanuk.library.models.DVD;
import com.booleanuk.library.payload.response.*;
import com.booleanuk.library.repository.DVDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dvds")
public class DVDController {
    @Autowired
    private DVDRepository dvdRepository;

    @GetMapping
    public ResponseEntity<DVDListResponse> getAllDVDs() {
        DVDListResponse dvdListResponse = new DVDListResponse();
        dvdListResponse.set(this.dvdRepository.findAll());
        return ResponseEntity.ok(dvdListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createDVD(@RequestBody DVD dvd) {
        DVDResponse response = new DVDResponse();
        try {
            response.set(this.dvdRepository.save(dvd));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getDVDById(@PathVariable int id) {
        DVD dvd = this.dvdRepository.findById(id).orElse(null);
        if (dvd == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        DVDResponse response = new DVDResponse();
        response.set(dvd);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateDVD(@PathVariable int id, @RequestBody DVD dvd) {
        DVD dvdToUpdate = this.dvdRepository.findById(id).orElse(null);
        if (dvdToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        dvdToUpdate.setTitle(dvd.getTitle());
        dvdToUpdate.setYear(dvd.getYear());
        dvdToUpdate.setGenre(dvd.getGenre());

        try {
            dvdToUpdate = this.dvdRepository.save(dvdToUpdate);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        DVDResponse response = new DVDResponse();
        response.set(dvdToUpdate);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteDVD(@PathVariable int id) {
        DVD dvdToDelete = this.dvdRepository.findById(id).orElse(null);
        if (dvdToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.dvdRepository.delete(dvdToDelete);
        DVDResponse response = new DVDResponse();
        response.set(dvdToDelete);
        return ResponseEntity.ok(response);
    }
}
