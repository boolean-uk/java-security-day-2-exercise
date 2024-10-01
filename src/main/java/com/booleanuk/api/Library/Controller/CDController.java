package com.booleanuk.api.Library.Controller;

import com.booleanuk.api.Library.Model.CD;
import com.booleanuk.api.Library.Repository.CDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("cds")
public class CDController {

    @Autowired
    private CDRepository repository;

    @PostMapping
    public ResponseEntity<CD> createCD(@RequestBody CD cd){


        try {

            return new ResponseEntity<CD>(this.repository.save(cd),
                    HttpStatus.CREATED);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create DVD, please check all required fields are correct.");
        }


    }

    @GetMapping
    public ResponseEntity<List<CD>> getAllCDs(){
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<CD> getOneCD(@PathVariable int id){
        CD cd=this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        return ResponseEntity.ok(cd);
    }

    @PutMapping("{id}")
    public ResponseEntity<CD> updateCD(@PathVariable int id,
                                         @RequestBody CD cd){
        CD cdToUpdate=this.repository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No CD with that ID found")
        );
        cdToUpdate.setTitle(cd.getTitle());
        cdToUpdate.setGenre(cd.getTitle());
        cdToUpdate.setArtist(cd.getArtist());
        cdToUpdate.setRecordCompany(cdToUpdate.getRecordCompany());
        return new ResponseEntity<CD>(this.repository.save(cdToUpdate
        ), HttpStatus.CREATED);



    }

    @DeleteMapping("{id}")
    public ResponseEntity<CD> deleteCD(@PathVariable int id){
        CD cdToDelete=this.repository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No CD with that ID found")
        );

        this.repository.delete(cdToDelete);
        return ResponseEntity.ok(cdToDelete);
    }
}
