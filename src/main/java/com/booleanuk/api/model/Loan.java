package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties(value = {"loans"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    @JsonIgnoreProperties(value = {"loans"})
    private VideoGame game;

    @Column
    private boolean borrowed;

    public Loan() {
        this.borrowed = true;
    }
}