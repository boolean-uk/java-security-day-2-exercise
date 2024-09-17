package com.booleanuk.api.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String type;

    public Item(String title, String type) {
        this.title = title;
        this.type = type;
    }

    public Item(int id) {
        this.id = id;
    }
}