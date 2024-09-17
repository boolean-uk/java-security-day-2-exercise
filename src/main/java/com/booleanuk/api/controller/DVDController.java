package com.booleanuk.api.controller;

import com.booleanuk.api.model.DVD;
import com.booleanuk.api.repository.DVDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("DVD")
public class DVDController {

    private final DVDRepository dvdRepository;

    @Autowired
    public DVDController(DVDRepository repository) {
        this.dvdRepository = repository;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<DVD>> getAll() {
        List<DVD> games = this.dvdRepository.findAll();
        return ResponseEntity.ok(games);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DVD> create(@RequestBody DVD body) {
        DVD dvd = this.dvdRepository.save(body);
        return new ResponseEntity<>(dvd, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DVD> update(@PathVariable int id, @RequestBody DVD body) {
        DVD dvd = this.dvdRepository.findById(id).orElse(null);
        if (dvd == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DVD not found");
        }
        else {
            dvd.setTitle(body.getTitle());
            dvd.setGenre(body.getGenre());
            dvd.setDirector(body.getDirector());
            dvd.setDuration(body.getDuration());
            dvd.setReleasedYear(body.getReleasedYear());
            this.dvdRepository.save(dvd);
        }
        return new ResponseEntity<>(dvd, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DVD> delete(@PathVariable int id) {
        DVD dvd = this.dvdRepository.findById(id).orElse(null);
        if (dvd == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DVD not found");
        } else {
            this.dvdRepository.delete(dvd);
        }
        return ResponseEntity.ok(dvd);
    }
}
