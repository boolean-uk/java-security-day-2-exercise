package com.booleanuk.api.controllers;

import com.booleanuk.api.models.VideoGame;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/video-games")
public class VideoGameController extends GenericController<VideoGame, Integer> {
}
