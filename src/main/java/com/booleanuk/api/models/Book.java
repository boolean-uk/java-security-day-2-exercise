package com.booleanuk.api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Books")
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private int numberOfPages;

    public Book() {}

    public Book(String title, String author, String publisher, String genre, int numberOfPages) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
        this.numberOfPages = numberOfPages;
    }
}
