package com.booleanuk.library.controller;

import com.booleanuk.library.model.VideoGame;

import com.booleanuk.library.repository.VideoGamesRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/videogames")
public class VideoGameController extends GenericController<VideoGame, VideoGamesRepository> {

    @Override
    protected void updateItemValues(VideoGame existingVideoGame, VideoGame updatedVideoGame){
        if (updatedVideoGame.getTitle() != null){
            existingVideoGame.setTitle(updatedVideoGame.getTitle());
        }
    }
}