package com.booleanuk.api.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;
    @Column
    private String type;
    @Column
    private String description;
    @Column
    private String genre;


    public Item(String title, String type, String description, String genre) {
        this.title = title;
        this.type = type;
        this.description = description;
        this.genre = genre;
    }

    public Item(int id){this.id = id;}
}
