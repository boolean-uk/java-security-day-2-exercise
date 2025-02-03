package com.booleanuk.api.library.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type")
    private ItemType itemType;

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

    public Item(String title, ItemType itemType, String author, String publisher, int year, String genre) {
        this.title = title;
        this.itemType = itemType;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.genre = genre;
    }

    public Item(int id) {
        this.id = id;
    }
}
