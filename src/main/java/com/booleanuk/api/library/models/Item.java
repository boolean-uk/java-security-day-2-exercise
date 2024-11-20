package com.booleanuk.api.library.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    private String type;

    @Column(name = "isBorrowed")
    private boolean isBorrowed;

    @ManyToOne
    @JoinColumn(name ="borrowed_by")
    @JsonIncludeProperties(value = {"name"})
    @JsonIgnoreProperties("borrowedBy")
    private User borrowedBy;


    public Item(String title, String type) {
        this.title = title;
        this.type = type;
    }
}
