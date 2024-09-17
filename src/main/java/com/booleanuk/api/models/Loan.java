package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"email", "password", "roles"})
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    @JsonIgnoreProperties({"addedAt"})
    private Item item;

    @Temporal(TemporalType.TIMESTAMP)
    private Date borrowedAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date returnedAt;

    public Loan(User user, Item item) {
        this.user = user;
        this.item = item;
    }

    public Loan(int id) {
        this.id = id;
    }
}
