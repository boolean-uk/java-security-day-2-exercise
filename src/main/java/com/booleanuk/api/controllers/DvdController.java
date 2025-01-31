package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Dvd;
import com.booleanuk.api.repository.DvdRepository;
import com.booleanuk.api.payload.response.DvdListResponse;
import com.booleanuk.api.payload.response.DvdResponse;
import com.booleanuk.api.payload.response.ErrorResponse;
import com.booleanuk.api.payload.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dvds")
public class DvdController {
    @Autowired
    private DvdRepository dvdRepository;

    @GetMapping
    public ResponseEntity<DvdListResponse> getAllDvds() {
        DvdListResponse dvdListResponse = new DvdListResponse();
        dvdListResponse.set(this.dvdRepository.findAll());
        return ResponseEntity.ok(dvdListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createDvd(@RequestBody Dvd dvd) {
        DvdResponse dvdResponse = new DvdResponse();
        try {
            dvdResponse.set(this.dvdRepository.save(dvd));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(dvdResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getDvdById(@PathVariable int id) {
        Dvd dvd = this.dvdRepository.findById(id).orElse(null);
        if (dvd == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        DvdResponse dvdResponse = new DvdResponse();
        dvdResponse.set(dvd);
        return ResponseEntity.ok(dvdResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateDvd(@PathVariable int id, @RequestBody Dvd dvd) {
        Dvd dvdToUpdate = this.dvdRepository.findById(id).orElse(null);
        if (dvdToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        dvdToUpdate.setTitle(dvd.getTitle());
        dvdToUpdate.setYear(dvd.getYear());

        try {
            dvdToUpdate = this.dvdRepository.save(dvdToUpdate);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        DvdResponse dvdResponse = new DvdResponse();
        dvdResponse.set(dvdToUpdate);
        return new ResponseEntity<>(dvdResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteDvd(@PathVariable int id) {
        Dvd dvdToDelete = this.dvdRepository.findById(id).orElse(null);
        if (dvdToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.dvdRepository.delete(dvdToDelete);
        DvdResponse dvdResponse = new DvdResponse();
        dvdResponse.set(dvdToDelete);
        return ResponseEntity.ok(dvdResponse);
    }
}
