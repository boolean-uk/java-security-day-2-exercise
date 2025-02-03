package com.booleanuk.api.library.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonIncludeProperties({"id", "username", "email"})
    @ManyToOne private User user;

    @ManyToOne private Item item;

    @Column private boolean isReturned;

    @Column private LocalDateTime loanedAt;

    public Loan(User user, Item item, boolean isReturned, LocalDateTime loanedAt) {
        this.user = user;
        this.item = item;
        this.isReturned = isReturned;
        this.loanedAt = loanedAt;
    }

    public Loan(boolean isReturned, LocalDateTime loanedAt) {
        this.isReturned = isReturned;
        this.loanedAt = loanedAt;
    }

    public int getUserId() {
        return user.getId();
    }
}
