package com.booleanuk.api.model;

import com.booleanuk.api.generic.GenericEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "dvds")
public class Dvd implements GenericEntity<Dvd> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private String name;

  @Override
  public void update(Dvd source) {
    this.name = source.name;
  }
}