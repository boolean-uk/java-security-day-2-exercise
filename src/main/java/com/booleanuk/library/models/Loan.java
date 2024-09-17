package com.booleanuk.library.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String createdAt;

    @Column
    private String updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties( value = { "loans", "loansHistory"})
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    @JsonIgnoreProperties( value = {"loans", "loansHistory"})
    private Item item;

    public Loan(String title, String createdAt, String updatedAt, User user, Item item) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.item = item;
        this.title = title;
    }

    public Loan(int id) {
        this.id = id;
    }
}
