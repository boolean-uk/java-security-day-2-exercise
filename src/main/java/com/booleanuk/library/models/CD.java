package com.booleanuk.library.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cds")
public class CD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "artist")
    private String artist;

    @Column(name = "year")
    private int year;

    @Column(name = "genre")
    private String genre;

    public CD(String title, String artist, int year, String genre) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.genre = genre;
    }

    public CD(int id) {
        this.id = id;
    }
}
