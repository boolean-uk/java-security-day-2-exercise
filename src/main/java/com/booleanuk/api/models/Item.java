package com.booleanuk.api.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;
    @Column
    private String genre;
    @Column
    private int year;

    public Item(String title, String genre, int year) {
        this.title = title;
        this.genre = genre;
        this.year = year;
    }
}
