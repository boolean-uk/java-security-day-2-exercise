package com.booleanuk.library.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "videoGames")
public class VideoGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String gameStudio;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private int numberOfPlayers;

    @Column(nullable = false)
    private String genre;

    public VideoGame(String title, String gameStudio, int rating, int numberOfPlayers, String genre) {
        this.title = title;
        this.gameStudio = gameStudio;
        this.rating = rating;
        this.numberOfPlayers = numberOfPlayers;
        this.genre = genre;
    }


}
