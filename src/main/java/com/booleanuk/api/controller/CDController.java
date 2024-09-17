package com.booleanuk.api.controller;

import com.booleanuk.api.model.CD;
import com.booleanuk.api.model.Game;
import com.booleanuk.api.repository.CDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("CD")
public class CDController {

    private final CDRepository cdRepository;

    @Autowired
    public CDController(CDRepository repository) {
        this.cdRepository = repository;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<CD>> getAll() {
        List<CD> cds = this.cdRepository.findAll();
        return ResponseEntity.ok(cds);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CD> create(@RequestBody CD body) {
        CD cd = this.cdRepository.save(body);
        return new ResponseEntity<>(cd, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CD> update(@PathVariable int id, @RequestBody CD body) {
        CD cd = this.cdRepository.findById(id).orElse(null);
        if (cd == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CD not found");
        }
        else {
            cd.setTitle(body.getTitle());
            cd.setGenre(body.getGenre());
            cd.setArtist(body.getArtist());
            cd.setReleasedYear(body.getReleasedYear());
            cd.setNumberOfTracks(body.getNumberOfTracks());
            this.cdRepository.save(cd);
        }
        return new ResponseEntity<>(cd, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CD> delete(@PathVariable int id) {
        CD cd = this.cdRepository.findById(id).orElse(null);
        if (cd == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CD not found");
        } else {
            this.cdRepository.delete(cd);
        }
        return ResponseEntity.ok(cd);
    }
}
