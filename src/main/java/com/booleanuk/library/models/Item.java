package com.booleanuk.library.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type")
    private String type;

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


    @OneToMany(mappedBy = "item")
    @JsonIgnoreProperties(value = "item", allowSetters = true)
    private List<Loan> loans;

    @OneToMany(mappedBy = "item")
    @JsonIgnoreProperties(value = "item", allowSetters = true)
    private List<Loan> loansHistory;

    public Item(String type, String title, String author, String publisher, int year, String genre) {
        this.type = type;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.genre = genre;
    }

    public Item(int id) {
        this.id = id;
    }
}
