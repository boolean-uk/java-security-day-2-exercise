package com.booleanuk.library.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "videogames")
public class Videogame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String gameStudio;

    @Column
    private String ageRating;

    @Column
    private int numberOfPlayers;

    @Column
    private String genre;

    public Videogame(String title, String gameStudio, String ageRating, int numberOfPlayers, String genre) {
        this.title = title;
        this.gameStudio = gameStudio;
        this.ageRating = ageRating;
        this.numberOfPlayers = numberOfPlayers;
        this.genre = genre;
    }
}