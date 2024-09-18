package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String dayBorrowed;

    @Column
    private String dayReturned;

    @ManyToOne
    @JoinColumn(name = "boardGame_id")
    @JsonIncludeProperties(value = {"title", "creator", "publisher", "year", "genre"})
    private BoardGame boardGame;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonIncludeProperties(value = {"title", "author", "publisher", "year", "genre"})
    private Book book;

    @ManyToOne
    @JoinColumn(name = "cd_id")
    @JsonIncludeProperties(value = {"title", "artist", "publisher", "year", "genre"})
    private CD cd;

    @ManyToOne
    @JoinColumn(name = "dvd_id")
    @JsonIncludeProperties(value = {"title", "director", "publisher", "year", "genre"})
    private DVD dvd;

    @ManyToOne
    @JoinColumn(name = "videoGame_id")
    @JsonIncludeProperties(value = {"title", "studio", "publisher", "year", "genre"})
    private VideoGame videoGame;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIncludeProperties(value = {"name", "email"})
    private User user;

    public Loan(int id){
        this.id = id;
    }
}
