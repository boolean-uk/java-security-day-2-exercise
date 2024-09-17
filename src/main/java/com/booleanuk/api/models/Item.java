package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
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

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_type")
    private String itemType;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    @JsonIncludeProperties(value = {"id, username"})
//    private User user;

    public Item(String itemName, String itemType) {
        this.itemName = itemName;
        this.itemType = itemType;
    }
}
