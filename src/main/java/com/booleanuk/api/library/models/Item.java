package com.booleanuk.api.library.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "book")
    private String book;

    @Column(name = "cds")
    private String CDs;

    @Column(name = "dvds")
    private String DVDs;

    @Column(name = "video_games")
    private String video_games;

    @Column(name = "board_games")
    private String board_games;

    public Item(String book, String CDs, String DVDs, String video_games, String board_games) {
        this.book = book;
        this.CDs = CDs;
        this.DVDs = DVDs;
        this.video_games = video_games;
        this.board_games = board_games;
    }
}
