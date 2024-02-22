package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "borrowed_item")
public class LoanedItem {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "borrow_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime borrowDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @PrePersist
    public void prePersist() {
        borrowDate = LocalDateTime.now();
    }

    public LoanedItem(User user, Item item) {
        this.user = user;
        this.item = item;
    }
}
