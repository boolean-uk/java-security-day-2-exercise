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
    @Column
    private String name;
    @Column
    private String type;
    @Column(name = "is_rented")
    private boolean isRented;

    public Item(String name, String type) {
        this.name = name;
        this.type = type;
        this.isRented = false;
    }
}
