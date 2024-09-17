package com.booleanuk.api.controllers;

import com.booleanuk.api.models.CD;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cds")
public class CDController extends GenericController<CD, Integer> {
}
