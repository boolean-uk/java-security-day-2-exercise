package com.booleanuk.api.model;

import com.booleanuk.api.generic.GenericEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "games")
public class Game implements GenericEntity<Game> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private String name;

  @Override
  public void update(Game source) {
    this.name = source.name;
  }
}
