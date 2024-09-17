package com.booleanuk.api.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class DVD extends Item {
    private String director;
    private int year;
    private String genre;

    public DVD(String title, String type, String director, int year, String genre) {
        super(title, type);
        this.director = director;
        this.year = year;
        this.genre = genre;
    }

    public DVD(int id) {
        super(id);
    }
}