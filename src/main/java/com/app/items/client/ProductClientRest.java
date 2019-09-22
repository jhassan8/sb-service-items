package com.app.items.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.app.commons.models.entity.Product;

@FeignClient(name = "service-products")
public interface ProductClientRest {

	@GetMapping("all")
	public List<Product> products();
	
	@GetMapping("view/{id}")
	public Product product(@PathVariable Long id);
	
	@PostMapping("new")
	public Product createProduct(@RequestBody Product product);
	
	@PutMapping("update/{id}")
	public Product updateProduct(@RequestBody Product product, @PathVariable Long id);
	
	@DeleteMapping("delete/{id}")
	public void deleteProduct(@PathVariable Long id);
	
}
