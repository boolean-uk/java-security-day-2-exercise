package com.booleanuk.api.library.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "format")
    private String format;

    @Column(name = "genre")
    private String genre;

    @Column(name = "isBorrowed")
    private Boolean isBorrowed;



    @OneToMany(mappedBy = "item")
    @ToString.Exclude
    private List<Borrow> borrows;
}