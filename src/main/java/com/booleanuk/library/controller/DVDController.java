package com.booleanuk.library.controller;

import com.booleanuk.library.model.DVD;
import com.booleanuk.library.repository.DVDRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dvds")
public class DVDController extends GenericController<DVD, DVDRepository> {

    @Override
    protected void updateItemValues(DVD existingDVD, DVD updatedDVD){
        if (updatedDVD.getTitle() != null){
            existingDVD.setTitle(updatedDVD.getTitle());
        }
    }
}