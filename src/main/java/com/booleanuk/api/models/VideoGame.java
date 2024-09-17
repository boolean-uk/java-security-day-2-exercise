package com.booleanuk.api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "video_games")
public class VideoGame extends Item {
    private String gameStudio;
    private int ageRating;
    private int numberOfPlayers;
    private String genre;

    public VideoGame(String title, String type, String gameStudio, int ageRating, int numberOfPlayers, String genre) {
        super(title, type);
        this.gameStudio = gameStudio;
        this.ageRating = ageRating;
        this.numberOfPlayers = numberOfPlayers;
        this.genre = genre;
    }

    public VideoGame(int id) {
        super(id);
    }
}