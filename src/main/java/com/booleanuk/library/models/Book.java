package com.booleanuk.library.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book extends Item{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "year")
    private int year;

    @Column(name = "genre")
    private String genre;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    @JsonIncludeProperties(value = {"username", "email"})
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_previous_users",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> previousUsers;

    public Book(String title, String author, String publisher, int year, String genre) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.genre = genre;
    }
}
