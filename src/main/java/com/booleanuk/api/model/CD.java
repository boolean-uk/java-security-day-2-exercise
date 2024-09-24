package com.booleanuk.api.model;

import jakarta.persistence.*;
import lombok.Data;
@Data

@Entity
@Table(name = "cds")
public class CD {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String name;
}

