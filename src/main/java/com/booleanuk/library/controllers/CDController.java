package com.booleanuk.library.controllers;

import com.booleanuk.library.models.BoardGame;
import com.booleanuk.library.models.CD;
import com.booleanuk.library.payload.response.*;
import com.booleanuk.library.repository.BoardGameRepository;
import com.booleanuk.library.repository.CDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cds")
public class CDController {

    @Autowired
    private CDRepository cdRepository;

    @GetMapping
    public ResponseEntity<CdListResponse> getAllBooks() {
        CdListResponse cdListResponse = new CdListResponse();
        cdListResponse.set(this.cdRepository.findAll());
        return ResponseEntity.ok(cdListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createBook(@RequestBody CD cd) {
        CdResponse cdResponse = new CdResponse();
        try {
            cdResponse.set(this.cdRepository.save(cd));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(cdResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getBookById(@PathVariable int id) {
        CD cd = this.cdRepository.findById(id).orElse(null);
        if (cd == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        CdResponse cdResponse = new CdResponse();
        cdResponse.set(cd);
        return ResponseEntity.ok(cdResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateBook(@PathVariable int id, @RequestBody CD cd) {
        CD cdToUpdate = this.cdRepository.findById(id).orElse(null);
        if (cdToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        cdToUpdate.setTitle(cd.getTitle());
        cdToUpdate.setArtist(cd.getArtist());
        cdToUpdate.setYear(cd.getYear());
        cdToUpdate.setGenre(cd.getGenre());

        try {
            cdToUpdate = this.cdRepository.save(cdToUpdate);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        CdResponse cdResponse = new CdResponse();
        cdResponse.set(cdToUpdate);
        return new ResponseEntity<>(cdResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteBook(@PathVariable int id) {
        CD cdToDelete = this.cdRepository.findById(id).orElse(null);
        if (cdToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.cdRepository.delete(cdToDelete);
        CdResponse cdResponse = new CdResponse();
        cdResponse.set(cdToDelete);
        return ResponseEntity.ok(cdResponse);
    }
}
