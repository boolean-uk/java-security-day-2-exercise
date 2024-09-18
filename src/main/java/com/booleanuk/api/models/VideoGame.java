package com.booleanuk.api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "VideoGames")
@Getter
@Setter
public class VideoGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String gameStudio;

    @Column(nullable = false)
    private String ageRating;

    @Column(nullable = false)
    private int numberOfPlayers;

    @Column(nullable = false)
    private String genre;

    public VideoGame() {}

    public VideoGame(String title, String gameStudio, String ageRating, int numberOfPlayers, String genre) {
        this.title = title;
        this.gameStudio = gameStudio;
        this.ageRating = ageRating;
        this.numberOfPlayers = numberOfPlayers;
        this.genre = genre;
    }
}
