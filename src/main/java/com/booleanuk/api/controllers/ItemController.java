package com.booleanuk.api.controllers;

import com.booleanuk.api.models.Item;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("items")
public class ItemController extends ControllerTemplate<Item> {
}
