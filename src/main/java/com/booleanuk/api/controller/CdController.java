package com.booleanuk.api.controller;

import com.booleanuk.api.generic.GenericController;
import com.booleanuk.api.model.Cd;
import com.booleanuk.api.repository.CdRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cds")
public class CdController extends GenericController<Cd> {
  public CdController(CdRepository repository) {
    super(repository);
  }
}