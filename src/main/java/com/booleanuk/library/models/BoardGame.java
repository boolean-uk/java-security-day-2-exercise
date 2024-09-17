package com.booleanuk.library.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "boardGames")
public class BoardGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "creator")
    private String creator;

    @Column(name = "year")
    private int year;

    @Column(name = "genre")
    private String genre;

    public BoardGame(String title, String creator, int year, String genre) {
        this.title = title;
        this.creator = creator;
        this.year = year;
        this.genre = genre;
    }

    public BoardGame(int id) {
        this.id = id;
    }
}
