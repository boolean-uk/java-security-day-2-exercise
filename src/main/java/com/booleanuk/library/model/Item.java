package com.booleanuk.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "available")
    private boolean available;

    public Item (int id, String title) {
        this.id = id;
        this.title = title;
        this.available = true;
    }


    // private List<Loan> loanHistory = new ArrayList<>();
}
