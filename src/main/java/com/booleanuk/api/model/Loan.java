package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "loans")
@NoArgsConstructor
@Data
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinTable(name = "user_id")
    private User user;

    @OneToOne
    private VideoGame videoGame;

    @Column
    private ZonedDateTime borrowedAt;

    @Column
    private ZonedDateTime dueBy;

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

    @Column
    private boolean returned;

    public Loan(
            User user,
            VideoGame videoGame,
            ZonedDateTime borrowedAt,
            ZonedDateTime dueBy,
            boolean returned
    )   {
        this.user =  user;
        this.videoGame =  videoGame;
        this.borrowedAt = borrowedAt;
        this.dueBy = dueBy;
        this.returned =   returned;
    }
}
