package com.booleanuk.api.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "video_games")
@Getter
@Setter
@NoArgsConstructor
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

    public Item(String title, String type, String description) {
        this.title = title;
        this.type = type;
        this.description = description;
    }

    public Item(int id){
        this.id = id;
    }
}
