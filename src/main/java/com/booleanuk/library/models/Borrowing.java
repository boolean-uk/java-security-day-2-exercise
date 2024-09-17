package com.booleanuk.library.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@Entity
@Table(name = "borrowings")
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "video_game_id")
    VideoGame videoGame;

    @ManyToOne
    @JoinColumn(name = "board_game_id")
    BoardGame boardGame;

    @ManyToOne
    @JoinColumn(name = "cd_id")
    CD cd;

    @ManyToOne
    @JoinColumn(name = "dvd_id")
    DVD dvd;

    @ManyToOne
    @JoinColumn(name = "book_id")
    Book book;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX")
    OffsetDateTime borrowedAt;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX")
    OffsetDateTime returnedAt;


    public Borrowing(User user, VideoGame videoGame, BoardGame boardGame, CD cd, DVD dvd, Book book) {
        this.user = user;
        this.videoGame = videoGame;
        this.boardGame = boardGame;
        this.cd = cd;
        this.dvd = dvd;
        this.book = book;
    }

    public Borrowing(int id) {
        this.id = id;
    }

    @PrePersist
    public void prePersist() {
        borrowedAt = OffsetDateTime.now();
    }
}
