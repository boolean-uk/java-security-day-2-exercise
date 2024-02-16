package com.booleanuk.api.models;


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

    @Column(name = "type")
    private String type;

    @Column(name = "is_rented")
    private boolean isRented;

    public Item(String title, String type) {
        this.title = title;
        this.type = type;
        this.isRented = false;
    }
}
