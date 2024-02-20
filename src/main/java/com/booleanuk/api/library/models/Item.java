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

    @Column(name = "title")
    private String title;

    @Column(name = "object")
    private String object;

    @Column(name = "available")
    private Boolean available;

    public Item(String title, String object, Boolean available) {
        this.title = title;
        this.object = object;
        this.available = available;
    }

    public Item(int id) {
        this.id = id;
    }
}