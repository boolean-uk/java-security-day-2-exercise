package com.booleanuk.library.models;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "dvds")
public class DVD {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "director")
    private String director;

    @Column(name = "year")
    private int year;

    @Column(name = "genre")
    private String genre;

    public DVD(String title, String director, int year, String genre) {
        this.title = title;
        this.director = director;
        this.year = year;
        this.genre = genre;
    }
}
