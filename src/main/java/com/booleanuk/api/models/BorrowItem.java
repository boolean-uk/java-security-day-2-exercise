package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BorrowItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "game_id")
    @JsonIncludeProperties(value = {"id", "title"})
    private Item item;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIncludeProperties(value = {"id", "name"})
    private User user;
    // Could be calculated from the endDate...
    private boolean isActive = false;
    @CreationTimestamp
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
