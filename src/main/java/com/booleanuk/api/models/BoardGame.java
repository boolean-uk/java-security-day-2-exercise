package com.booleanuk.api.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class BoardGame extends Item {
    private String gameStudio;
    private int ageRating;
    private int numberOfPlayers;
    private String genre;

    public BoardGame(String title, String type, String gameStudio, int ageRating, int numberOfPlayers, String genre) {
        super(title, type);
        this.gameStudio = gameStudio;
        this.ageRating = ageRating;
        this.numberOfPlayers = numberOfPlayers;
        this.genre = genre;
    }

    public BoardGame(int id) {
        super(id);
    }
}
