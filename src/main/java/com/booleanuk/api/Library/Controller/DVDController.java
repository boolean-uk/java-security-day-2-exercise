package com.booleanuk.api.Library.Controller;

import com.booleanuk.api.Library.Model.DVD;
import com.booleanuk.api.Library.Repository.DVDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("dvds")
public class DVDController {

    @Autowired
    private DVDRepository repository;

    @PostMapping
    public ResponseEntity<DVD> createDVD(@RequestBody DVD dvd){


        try {

            return new ResponseEntity<DVD>(this.repository.save(dvd),
                    HttpStatus.CREATED);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create DVD, please check all required fields are correct.");
        }


    }

    @GetMapping
    public ResponseEntity<List<DVD>> getAllDVDs(){
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<DVD> getOneDVD(@PathVariable int id){
        DVD dvd=this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        return ResponseEntity.ok(dvd);
    }

    @PutMapping("{id}")
    public ResponseEntity<DVD> updateDVD(@PathVariable int id,
                                           @RequestBody DVD dvd){
        DVD dvdToUpdate=this.repository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No DVD with that ID found")
        );
        dvdToUpdate.setTitle(dvd.getTitle());
        dvdToUpdate.setGenre(dvd.getGenre());
        dvdToUpdate.setAgeRating(dvd.getAgeRating());
        dvdToUpdate.setStudio(dvd.getStudio());
        return new ResponseEntity<DVD>(this.repository.save(dvdToUpdate
        ), HttpStatus.CREATED);



    }

    @DeleteMapping("{id}")
    public ResponseEntity<DVD> deleteDVD(@PathVariable int id){
        DVD dvdToDelete=this.repository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No DVD with that ID found")
        );

        this.repository.delete(dvdToDelete);
        return ResponseEntity.ok(dvdToDelete);
    }
}
