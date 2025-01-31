package com.booleanuk.api.library.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "itemType")
    private EItem itemType;

    @Column(name = "available")
    private boolean available;

    public Item(String name, EItem itemType) {
        this.name = name;
        this.itemType = itemType;
        available = true;
    }

    public Item(int id) {
        this.id = id;
        available = true;
    }

    public Item(){
        available = true;
    }
}