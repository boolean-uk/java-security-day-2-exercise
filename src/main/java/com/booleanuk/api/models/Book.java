package com.booleanuk.api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "books")
public class Book extends Item {
    private String author;
    private int year;
    private String genre;

    public Book(String title, String type, String author, int year, String genre) {
        super(title, type);
        this.author = author;
        this.year = year;
        this.genre = genre;
    }

    public Book(int id) {
        super(id);
    }
}
