package com.booleanuk.api.Library.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @Column
    private String genre;

    @Column
    private String author;

    @Column
    private String publisher;

    @ManyToOne
    @JsonIgnoreProperties(value ={"users", "games"})
    @JoinColumn(name="userId")
    private User user;

    public Book(String title, String genre, String author, String publisher) {
        this.title = title;
        this.genre = genre;
        this.author = author;
        this.publisher = publisher;
    }
}