package com.boot.productapi.controller;

import com.boot.productapi.dto.ProductDto;
import com.boot.productapi.entity.Product;
import com.boot.productapi.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {
	@Autowired
	private final ProductService productService;
	private ModelMapper modelMapper;

	// Create a new product
	@PostMapping("/product")
	public ResponseEntity<Product> saveProduct(@Valid @RequestBody  Product product) {
		ResponseEntity<Product> newProduct = productService.saveProduct(product);
		URI uri = URI.create("/product/" + Objects.requireNonNull(newProduct.getBody()).getId());
		return ResponseEntity.created(uri).body(newProduct.getBody());
	}

	// Get all products
	@GetMapping("/product")
	public ResponseEntity<List<Product>> getAllProducts() {
		return productService.fetchAllProducts();
	}
	// Get a product by ID
	@GetMapping("/product/{id}")
	public ResponseEntity<Optional<Product>> getProductById(@PathVariable Long id) {
		ResponseEntity<Optional<Product>> product = productService.fetchProductById(id);
		if (product != null) {
			return product;
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Update a product
	@PutMapping(path = "/product/{productId}")
	public ResponseEntity<Product> updateProduct(@PathVariable(value = "productId") Long productId, @RequestBody Product product)
	{
		return productService.updateProduct(productId,product);
	}

	//Delete a product
	@DeleteMapping(value = "/product/{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
		return productService.deleteProduct(productId);

	}

	private ProductDto entity2Dto(Product entity) {
		return modelMapper.map(entity, ProductDto.class);
	}

	private List<ProductDto> list2Dto(List<Product> listUsers) {
		return listUsers.stream().map(
						this::entity2Dto)
				.collect(Collectors.toList());
	}

}
