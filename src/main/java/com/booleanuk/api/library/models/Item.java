package com.booleanuk.api.library.models;

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

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "on_loan")
    private boolean onLoan;

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE) // not sure about the remove here
//    @JsonManagedReference
    private List<Loan> loansHistory;

    public Item(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Item (int id) {
        this.id = id;
    }
}
