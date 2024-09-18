package com.booleanuk.library.controller;

import com.booleanuk.library.model.CD;
import com.booleanuk.library.repository.CDRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cds")
public class CDController extends GenericController<CD, CDRepository> {

    @Override
    protected void updateItemValues(CD existingCD, CD updatedCD){
        if (updatedCD.getTitle() != null){
            existingCD.setTitle(updatedCD.getTitle());
        }
    }
}