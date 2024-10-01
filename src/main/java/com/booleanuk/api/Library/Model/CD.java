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
@Table(name="cds")
public class CD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column
    private String title;

    @Column
    private String artist;

    @Column
    private String recordCompany;

    @Column
    private String genre;


    @ManyToOne
    @JsonIgnoreProperties(value ={"users", "games"})
    @JoinColumn(name="userId")
    private User user;

    public CD(String title, String recordCompany, String artist, String genre){
        this.title=title;
        this.artist=artist;
        this.recordCompany=recordCompany;
        this.genre=genre;

    }
}


