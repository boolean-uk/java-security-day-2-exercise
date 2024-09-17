package com.booleanuk.api.controllers;

import com.booleanuk.api.models.DVD;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dvds")
public class DVDController extends GenericController<DVD, Integer> {
}
