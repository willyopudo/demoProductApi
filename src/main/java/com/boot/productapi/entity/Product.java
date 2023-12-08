package com.boot.productapi.entity;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Name is required.")
	@NotNull(message = "The name is required.")
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@NotNull(message = "The price is required.")
	private double price;

	@Column(nullable = false)
	@NotNull(message = "The quantity is required.")
	private int quantity;

	// Constructors, getters and setters, and other methods...
}
