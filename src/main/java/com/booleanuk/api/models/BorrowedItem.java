package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "borrowed_items")
public class BorrowedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate borrowDate;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;

    public BorrowedItem(User user, Item item) {
        this.user = user;
        this.item = item;
    }
}
