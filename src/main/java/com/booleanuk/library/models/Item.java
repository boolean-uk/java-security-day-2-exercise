package com.booleanuk.library.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
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
    private String genre;
    @Column
    private Boolean isBorrowed;

    @OneToMany(mappedBy = "item")
    @ToString.Exclude
    private List<Borrow> borrow;
}
