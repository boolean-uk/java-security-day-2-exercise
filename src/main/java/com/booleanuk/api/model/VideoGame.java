package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "video_games")
@NoArgsConstructor
@Data
public class VideoGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String developer;

    @Column
    private String publisher;

    @Column
    private int    year;

    @Column
    private String rating;

    @Column
    private String genre;

    @Column
    private String platform;

    @CreationTimestamp
    @JsonIgnore
    private ZonedDateTime createdAt;
    @UpdateTimestamp
    @JsonIgnore
    private ZonedDateTime updatedAt;
    @PrePersist
    private void onCreate() {
        this.updatedAt = this.createdAt = ZonedDateTime.now();
    }
    @PreUpdate
    private void onUpdate() {
        this.updatedAt = ZonedDateTime.now();
    }

    public VideoGame(
            String title,
            String developer,
            String publisher,
            int    year,
            String rating,
            String genre,
            String platform
    )   {
        this.title =     title;
        this.developer = developer;
        this.publisher = publisher;
        this.year =      year;
        this.rating =    rating;
        this.genre =     genre;
        this.platform =  platform;
    }
}
