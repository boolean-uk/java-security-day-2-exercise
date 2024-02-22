package com.booleanuk.api.models;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column(name = "type")
    private String type;

    @Column(name = "loan_status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Item(String title, String type, String status) {
        this.title = title;
        this.type = type;
        this.status = status;
    }
}
