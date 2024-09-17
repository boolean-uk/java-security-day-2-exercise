package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "Loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "returned")
    private boolean returned;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIncludeProperties("username")
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    @JsonIncludeProperties({"title", "type"})
    private Item item;

    public Loan() {
        this.returned = false;
    }
}
