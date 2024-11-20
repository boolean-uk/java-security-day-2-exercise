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

    @Column
    private String title;

    @Column
    private String artist;

    @Column
    private int tracks;

    @Column
    private String genre;

    public CD(String title, String artist, int tracks, String genre) {
        this.title = title;
        this.artist = artist;
        this.tracks = tracks;
        this.genre = genre;
    }
}
