package com.booleanuk.library.models;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cds")
public class CD {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "title")
	private String title;

	@Column(name = "artist")
	private String artist;

	@Column(name = "genre")
	private String genre;

	public CD(int id) {
		this.id = id;
	}

	public CD(String title, String artist, String genre) {
		this.title = title;
		this.artist = artist;
		this.genre = genre;
	}
}

