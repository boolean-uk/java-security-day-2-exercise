package com.booleanuk.api.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "video_games")
public class VideoGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;
    @Column
    private String game_studio;
    @Column
    private String age_rating;
    @Column
    private int numberOfPlayers;
    @Column
    private String genre;


    public VideoGame(String title, String game_studio, String age_rating, int numberOfPlayers, String genre) {
        this.title = title;
        this.game_studio = game_studio;
        this.age_rating = age_rating;
        this.numberOfPlayers = numberOfPlayers;
        this.genre = genre;
    }

    public VideoGame(int id){this.id = id;}
}
