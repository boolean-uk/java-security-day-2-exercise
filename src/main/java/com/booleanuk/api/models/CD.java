package com.booleanuk.api.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CD extends Item {
    private String artist;
    private int year;
    private String genre;

    public CD(String title, String type, String artist, int year, String genre) {
        super(title, type);
        this.artist = artist;
        this.year = year;
        this.genre = genre;
    }

    public CD(int id) {
        super(id);
    }
}