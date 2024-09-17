package com.booleanuk.api.controller;

import com.booleanuk.api.generic.GenericController;
import com.booleanuk.api.model.Dvd;
import com.booleanuk.api.repository.DvdRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dvds")
public class DvdController extends GenericController<Dvd> {
  public DvdController(DvdRepository repository) {
    super(repository);
  }
}