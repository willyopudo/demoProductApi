package com.boot.productapi.test;

import com.boot.productapi.controller.ProductController;
import com.boot.productapi.entity.Product;
import com.boot.productapi.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
	private static final String END_POINT_PATH = "/api/v1/product";
	@Autowired
	private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	@MockBean
	private ProductService service;

	@Test
	public void testAddShouldReturn400BadRequest() throws Exception {
		Product newProduct = new Product(0L,"", 0, 1);

		String requestBody = objectMapper.writeValueAsString(newProduct);

		mockMvc.perform(post(END_POINT_PATH).contentType("application/json")
						.content(requestBody))
				.andExpect(status().isBadRequest())
				.andDo(print())
		;
	}
	@Test
	public void testAddShouldReturn201Created() throws Exception {
		Product newProduct = new Product(0L,"My first product", 30.5, 10);

		Mockito.when(service.saveProduct(newProduct)).thenReturn(new ResponseEntity<Product>(HttpStatusCode.valueOf(201)));

		String requestBody = objectMapper.writeValueAsString(newProduct);

		mockMvc.perform(post(END_POINT_PATH).contentType("application/json")
						.content(requestBody))
				.andExpect(status().isCreated())
				.andDo(print())
		;

	}
}
