package com.booleanuk.api.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@Entity
@Table(name = "user_items")
public class UserItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "borrowed_at")
    private LocalDateTime borrowedAt;

    @Column(name = "returned_at")
    private LocalDateTime returnedAt;

    @Column(name = "is_ongoing")
    private boolean isOngoing;

    public UserItem(User user, Item item, LocalDateTime borrowedAt, boolean isOngoing) {
        this.user = user;
        this.item = item;
        this.borrowedAt = borrowedAt;
        this.isOngoing = isOngoing;
    }
}

