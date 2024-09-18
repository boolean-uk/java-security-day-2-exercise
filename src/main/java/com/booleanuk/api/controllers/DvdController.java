package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Dvd;
import com.booleanuk.api.repository.DvdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/dvds")
public class DvdController {

    @Autowired
    private DvdRepository dvdRepository;

    @PostMapping
    public ResponseEntity<Dvd> createDvd(@RequestBody Dvd dvd) {
        return new ResponseEntity<>(this.dvdRepository.save(dvd), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Dvd>> getAllDvds() {
        return ResponseEntity.ok(this.dvdRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dvd> getOneDvd(@PathVariable int id) {
        Dvd dvd = this.dvdRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dvd with ID " + id + " not found."));
        return ResponseEntity.ok(dvd);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Dvd> updateDvd(@PathVariable int id, @RequestBody Dvd dvdDetails) {
        Dvd dvdToUpdate = this.dvdRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dvd with ID " + id + " not found."));

        dvdToUpdate.setTitle(dvdDetails.getTitle());
        dvdToUpdate.setDirector(dvdDetails.getDirector());
        dvdToUpdate.setGenre(dvdDetails.getGenre());
        dvdToUpdate.setReleaseYear(dvdDetails.getReleaseYear());
        dvdToUpdate.setDurationInMinutes(dvdDetails.getDurationInMinutes());

        return new ResponseEntity<>(this.dvdRepository.save(dvdToUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDvd(@PathVariable int id) {
        Dvd dvdToDelete = this.dvdRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dvd with ID " + id + " not found."));
        this.dvdRepository.delete(dvdToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
