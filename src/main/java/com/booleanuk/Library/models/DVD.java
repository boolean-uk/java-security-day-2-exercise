package com.booleanuk.Library.models;

import jakarta.persistence.*;

@Entity
@Table(name = "dvds")
public class DVD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "director")
    private String director;

    @Column(name = "genre")
    private String genre;

    public DVD() {
    }

    public DVD(int id) {
        this.id = id;
    }

    public DVD(String title, String director, String genre) {
        this.title = title;
        this.director = director;
        this.genre = genre;
    }
}