package com.booleanuk.api.Library.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name="games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column
    private String title;

    @Column
    private String gameStudio;

    @Column
    private String ageRating;

    @Column
    private String genre;

    @Column
    private int playerNumber;

    @ManyToOne
    @JsonIgnoreProperties(value ={"users", "games"})
    @JoinColumn(name="userId")
    private User user;

    public Game(String title, String gameStudio, String ageRating, String genre, int playerNumber){
        this.title=title;
        this.gameStudio=gameStudio;
        this.ageRating=ageRating;
        this.genre=genre;
        this.playerNumber=playerNumber;
    }

}
