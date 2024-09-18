package com.booleanuk.Library.models;

import jakarta.persistence.*;

@Entity
@Table(name = "video_games")
public class VideoGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "game_studio")
    private String gameStudio;

    @Column(name = "age_rating")
    private String ageRating;

    @Column(name = "number_of_players")
    private int numberOfPlayers;

    @Column(name = "genre")
    private String genre;

    public VideoGame() {
    }

    public VideoGame(int id) {
        this.id = id;
    }

    public VideoGame(String title, String gameStudio, String ageRating, int numberOfPlayers, String genre) {
        this.title = title;
        this.gameStudio = gameStudio;
        this.ageRating = ageRating;
        this.numberOfPlayers = numberOfPlayers;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGameStudio() {
        return gameStudio;
    }

    public void setGameStudio(String gameStudio) {
        this.gameStudio = gameStudio;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}