package com.booleanuk.library.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "currently_loaned")
    private boolean currentlyLoaned;

    @OneToOne
    @JoinColumn(name = "video_game_id", nullable = false)
    @JsonIncludeProperties(value = {"id", "title"})
    private VideoGame videoGame;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIncludeProperties(value = {"id", "name"})
    private User user;
}
