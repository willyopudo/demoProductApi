package com.boot.productapi.services;

import com.boot.productapi.entity.Product;
import com.boot.productapi.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {
	@Autowired
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public ResponseEntity<Product> saveProduct(Product product) {
		Product newProduct = productRepository.save(product);
		return ResponseEntity.ok(newProduct);
	}
	// Get all products
	public ResponseEntity<List<Product>> fetchAllProducts() {
		return ResponseEntity.ok(productRepository.findAll());
	}

	public ResponseEntity<Optional<Product>> fetchProductById(Long id){
		Optional<Product> product = productRepository.findById(id);
		if(product.isPresent())
			return ResponseEntity.ok(product);
		else
			return ResponseEntity.notFound().build();
	}
	public ResponseEntity<Product> updateProduct(Long id, Product updatedProduct){
		if (id == null) {
			throw new IllegalArgumentException("ID cannot be null");
		}
		if(!Objects.equals(updatedProduct.getId(), id))
			return ResponseEntity.status(403).build();
		Product Existingproduct = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
		Existingproduct.setName(updatedProduct.getName());
		Existingproduct.setPrice(updatedProduct.getPrice());
		Existingproduct.setQuantity(updatedProduct.getQuantity());
		Product savedEntity = productRepository.save(Existingproduct);
		return ResponseEntity.ok(savedEntity);
	}

	public ResponseEntity<String> deleteProduct(Long id) {
		Optional<Product> existingProduct = productRepository.findById(id);
		if(existingProduct.isPresent()) {
			productRepository.deleteById(id);
			return ResponseEntity.ok("Product Deleted Successfully against id " + id + " ");
		}
		else
			return ResponseEntity.notFound().build();
	}
}
