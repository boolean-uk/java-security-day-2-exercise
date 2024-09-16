package com.booleanuk.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "item")
public class Item extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private ItemType type;
    @Column
    private Boolean isLent;

    @Override
    public boolean haveNullFields() {
        return name == null || type == ItemType.INVALID || isLent == null;
    }

    @Override
    public void copyOverData(Model model) {
        Item _item = (Item) model;

        name = _item.name;
        type = _item.type;
        isLent = _item.isLent;

        //history = _item.history;
    }
}
