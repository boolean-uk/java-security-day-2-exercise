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
@Table(name="dvds")
public class DVD {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @Column
    private String title;

    @Column
    private String studio;

    @Column
    private String ageRating;

    @Column
    private String genre;


    @ManyToOne
    @JsonIgnoreProperties(value ={"users", "games"})
    @JoinColumn(name="userId")
    private User user;

    public DVD(String title, String studio, String ageRating, String genre){
        this.title=title;
        this.studio=studio;
        this.ageRating=ageRating;
        this.genre=genre;

    }
}




