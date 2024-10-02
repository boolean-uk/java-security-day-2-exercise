package com.booleanuk.api.controller;

import com.booleanuk.api.models.DVD;
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
    public ResponseEntity<DVD> createDvd(@RequestBody DVD dvd) {
        return new ResponseEntity<>(this.dvdRepository.save(dvd), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<DVD>> getAllDvds() {
        return ResponseEntity.ok(this.dvdRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DVD> getOneDvd(@PathVariable int id) {
        DVD dvd = this.dvdRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dvd with ID " + id + " not found."));
        return ResponseEntity.ok(dvd);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DVD> updateDvd(@PathVariable int id, @RequestBody DVD dvdDetails) {
        DVD dvdToUpdate = this.dvdRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dvd with ID " + id + " not found."));

        dvdToUpdate.setTitle(dvdDetails.getTitle());
        dvdToUpdate.setDirector(dvdDetails.getDirector());
        dvdToUpdate.setGenre(dvdDetails.getGenre());
        dvdToUpdate.setYear(dvdDetails.getYear());
        dvdToUpdate.setDurationInMinutes(dvdDetails.getDurationInMinutes());

        return new ResponseEntity<>(this.dvdRepository.save(dvdToUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDvd(@PathVariable int id) {
        DVD dvdToDelete = this.dvdRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dvd with ID " + id + " not found."));
        this.dvdRepository.delete(dvdToDelete);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
