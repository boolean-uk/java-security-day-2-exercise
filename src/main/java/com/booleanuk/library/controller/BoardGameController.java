package com.booleanuk.library.controller;

import com.booleanuk.library.model.BoardGame;

import com.booleanuk.library.repository.BoardGameRepository;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/boardgames")
public class BoardGameController extends GenericController<BoardGame, BoardGameRepository> {

    @Override
    protected void updateItemValues(BoardGame existingBoardGame, BoardGame updatedBoardGame){
        if (updatedBoardGame.getTitle() != null){
            existingBoardGame.setTitle(updatedBoardGame.getTitle());
        }
    }
}