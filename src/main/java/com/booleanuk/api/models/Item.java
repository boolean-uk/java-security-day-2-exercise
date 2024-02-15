package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column
    private String name;

    @Column
    private EItemType type;

    @Column
    private String description;

    @OneToMany(mappedBy = "item")
    @JsonIgnore
    private List<BorrowedItem> borrowedItems;

    public Item(String name, EItemType type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }
}
