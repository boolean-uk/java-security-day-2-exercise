package com.booleanuk.api.models;

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

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "type")
    private String type;

    @Column(name = "age_rating")
    private String ageRating;

    @Column(name = "quantity")
    private int quantity;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<ItemLoan> loans;

    public Item(String title, String author, String type, String ageRating, int quantity) {
        this.title = title;
        this.author = author;
        this.type = type;
        this.ageRating = ageRating;
        this.quantity = quantity;
    }
}