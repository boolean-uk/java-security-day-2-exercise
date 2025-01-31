package com.booleanuk.api.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "DVDs")
public class Dvd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "year")
    private int year;

    public Dvd(String title, int year) {
        this.title = title;
        this.year = year;
    }

    public Dvd(int id) {
        this.id = id;
    }
}
