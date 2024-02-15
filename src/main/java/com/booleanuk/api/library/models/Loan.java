package com.booleanuk.api.library.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIncludeProperties(value = {"name"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    @JsonIncludeProperties(value = {"title"})
    private Item item;

    @Column
    private LocalDateTime borrowedAt;

    @Column
    private LocalDateTime returnedAt;


    public Loan(User user, Item item) {
        this.user = user;
        this.item = item;
    }

    public Loan(int id){ this.id = id;}

}
