package com.app.items.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.app.items.models.Product;

@FeignClient(name = "service-product", url = "localhost:8001")
public interface ProductClientRest {

	@GetMapping("products")
	public List<Product> products();
	
	@GetMapping("product/{id}")
	public Product product(@PathVariable Long id);
	
}
