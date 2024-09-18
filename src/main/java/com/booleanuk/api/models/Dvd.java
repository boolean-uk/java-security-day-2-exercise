package com.booleanuk.api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Dvds")
@Getter
@Setter
public class Dvd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String director;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private int releaseYear;

    @Column(nullable = false)
    private int durationInMinutes;

    public Dvd() {}

    public Dvd(String title, String director, String genre, int releaseYear, int durationInMinutes) {
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.durationInMinutes = durationInMinutes;
    }
}
