package com.booleanuk.api.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dvds")
public class DVD {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String director;

    @Column(nullable = false)
    private int durationInMinutes;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private String genre;

    public DVD(String title, String director, int durationInMinutes, int year, String genre) {
        this.title = title;
        this.director = director;
        this.durationInMinutes = durationInMinutes;
        this.year = year;
        this.genre = genre;
    }
}

