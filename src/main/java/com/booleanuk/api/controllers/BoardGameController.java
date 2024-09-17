package com.booleanuk.api.controllers;

import com.booleanuk.api.models.BoardGame;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board-games")
public class BoardGameController extends GenericController<BoardGame, Integer> {
}
