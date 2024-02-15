package com.booleanuk.library.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "board_games")
public class BoardGame {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "publisher")
	private String publisher;

	@Column(name = "year")
	private int year;

	public BoardGame(int id) {
		this.id = id;
	}

	public BoardGame(String name, String publisher, int year) {
		this.name = name;
		this.publisher = publisher;
		this.year = year;
	}
}
