package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String phone;

    @CreationTimestamp
    @JsonIgnore
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @JsonIgnore
    private ZonedDateTime updatedAt;

    @PrePersist
    private void onCreated() {
        this.updatedAt = this.createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = ZonedDateTime.now();
    }

    public User(
            String name,
            String email,
            String phone
    )   {
        this.name =  name;
        this.email = email;
        this.phone = phone;
    }
}
