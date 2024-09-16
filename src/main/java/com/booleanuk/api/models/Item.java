package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Book.class, name = "book"),
        @JsonSubTypes.Type(value = CD.class, name = "cd"),
        @JsonSubTypes.Type(value = DVD.class, name = "dvd"),
        @JsonSubTypes.Type(value = VideoGame.class, name = "videoGame"),
        @JsonSubTypes.Type(value = BoardGame.class, name = "boardGame")
})
public abstract class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE) // Change the strategy to GenerationType.TABLE
    private int id;

    private String name;
    private double price;

    @ManyToOne
    @JoinColumn(name = "user_id") // Assuming the foreign key column name is user_id
    @JsonIgnoreProperties("items")
    private User user;
}
