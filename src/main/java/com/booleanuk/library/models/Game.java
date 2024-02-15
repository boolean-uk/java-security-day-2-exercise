package com.booleanuk.library.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String title;
    @Column
    private String studio;
    @Column
    private Integer ageRating;
    @Column
    private String genre;
    @Column
    private Boolean isBorrowed;

    @OneToMany(mappedBy = "game")
    @ToString.Exclude
    private List<Borrow> borrow;
}
