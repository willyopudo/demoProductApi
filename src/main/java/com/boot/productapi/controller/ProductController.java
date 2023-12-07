package com.boot.productapi.controller;

import com.boot.productapi.entity.Product;
import com.boot.productapi.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {
	@Autowired
	private final ProductService productService;

	// Create a new product
	@PostMapping("/product")
	public ResponseEntity<ResponseEntity<Product>> saveProduct(@RequestBody Product product) {
		ResponseEntity<Product> newProduct = productService.saveProduct(product);
		return ResponseEntity.ok(newProduct);
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

}
