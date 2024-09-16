package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "lend")
public class Lend extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    @ManyToOne
    @JoinColumn
    @JsonIncludeProperties(value = { "username", "email" })
    private User user;

    @ManyToOne
    @JoinColumn
    @JsonIncludeProperties(value = { "name", "type" })
    private Item item;

    public Lend(final User user, final Item item) {
        this.user = user;
        this.item = item;
    }

    @Override
    public boolean haveNullFields() {
        return user == null || item == null;
    }

    @Override
    public void copyOverData(Model model) {

    }
}
