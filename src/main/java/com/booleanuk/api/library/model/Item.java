package com.booleanuk.api.library.model;

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

    @Column(name = "type")
    private String type;

    @Column(name = "isBorrowed")
    private Boolean isBorrowed;

    public Item(String name, String type) {
        this.name = name;
        this.type = type;
        this.isBorrowed = false;
    }

    public Item(int id) {
        this.id = id;
        this.isBorrowed = false;
    }

    public Item() {
        this.isBorrowed = false;
    }
}
