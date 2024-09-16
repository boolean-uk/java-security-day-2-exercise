package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
//@Entity
//@Table(name = "history")
public class History extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn
    @JsonIncludeProperties(value = { "name", "type" })
    private Item item; // the item that was borrowed

    @ManyToOne
    @JoinColumn
    @JsonIncludeProperties(value = { "username", "email" })
    private User user; // the user who borrowed the item

    @Override
    public boolean haveNullFields() {
        return false;
    }

    @Override
    public void copyOverData(Model model) {

    }
}
