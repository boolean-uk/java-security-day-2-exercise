package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Cd;
import com.booleanuk.api.repository.CdRepository;
import com.booleanuk.api.payload.response.CdListResponse;
import com.booleanuk.api.payload.response.CdResponse;
import com.booleanuk.api.payload.response.ErrorResponse;
import com.booleanuk.api.payload.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cds")
public class CdController {
    @Autowired
    private CdRepository cdRepository;

    @GetMapping
    public ResponseEntity<CdListResponse> getAllCds() {
        CdListResponse cdListResponse = new CdListResponse();
        cdListResponse.set(this.cdRepository.findAll());
        return ResponseEntity.ok(cdListResponse);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createCd(@RequestBody Cd cd) {
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
    public ResponseEntity<Response<?>> getCdById(@PathVariable int id) {
        Cd cd = this.cdRepository.findById(id).orElse(null);
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
    public ResponseEntity<Response<?>> updateCd(@PathVariable int id, @RequestBody Cd cd) {
        Cd cdToUpdate = this.cdRepository.findById(id).orElse(null);
        if (cdToUpdate == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        cdToUpdate.setTitle(cd.getTitle());
        cdToUpdate.setYear(cd.getYear());

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
    public ResponseEntity<Response<?>> deleteCd(@PathVariable int id) {
        Cd cdToDelete = this.cdRepository.findById(id).orElse(null);
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

