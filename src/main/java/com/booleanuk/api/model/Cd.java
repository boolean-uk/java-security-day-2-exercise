package com.booleanuk.api.model;

import com.booleanuk.api.generic.GenericEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "cds")
public class Cd implements GenericEntity<Cd>  {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(nullable = false)
  private String name;

  @Override
  public void update(Cd source) {
    this.name = source.name;
  }
}
