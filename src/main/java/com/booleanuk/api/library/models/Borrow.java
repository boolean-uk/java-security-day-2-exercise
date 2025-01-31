package com.booleanuk.api.library.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "borrows")
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private LocalDate borrowed;

    @Column
    private LocalDate returned;

    @ManyToOne
    @JoinColumn(name="users")
    private User user;

    @ManyToOne
    @JoinColumn(name="items")
    private Item item;



    public Borrow(User user, Item item, LocalDate borrowed){
        this.user = user;
        this.item = item;
        this.borrowed = LocalDate.now();
        this.returned = null;
    }

    public Borrow(){

    }
}
