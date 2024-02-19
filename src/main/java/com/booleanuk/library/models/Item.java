package com.booleanuk.library.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "author_creator")
    private String authorCreator;

    @Column(name = "publisher_producer")
    private String publisherProducer;

    @Column(name = "year")
    private int year;

    @Column(name = "genre_category")
    private String genreCategory;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIncludeProperties(value = {"id", "name", "type","author_creator", "publisher_producer","year", "genre_category"})
    private Set<Loan> loans = new HashSet<>();

    public Item(String name, String type, String authorCreator, String publisherProducer, int year, String genreCategory) {
        this.name = name;
        this.type = type;
        this.authorCreator = authorCreator;
        this.publisherProducer = publisherProducer;
        this.year = year;
        this.genreCategory = genreCategory;
    }
}
