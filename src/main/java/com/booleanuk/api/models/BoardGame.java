package com.booleanuk.api.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@NoArgsConstructor
@Entity
@Table(name = "BoardGames")
@Getter
@Setter
public class BoardGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private int minPlayers;

    @Column(nullable = false)
    private int maxPlayers;

    @Column(nullable = false)
    private int playTimeInMinutes;

    public BoardGame(String title, String publisher, String genre, int minPlayers, int maxPlayers, int playTimeInMinutes) {
        this.title = title;
        this.publisher = publisher;
        this.genre = genre;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.playTimeInMinutes = playTimeInMinutes;
    }
}

