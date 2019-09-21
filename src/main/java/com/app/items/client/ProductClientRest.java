package com.app.items.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.app.items.models.Product;

@FeignClient(name = "service-products")
public interface ProductClientRest {

	@GetMapping("all")
	public List<Product> products();
	
	@GetMapping("view/{id}")
	public Product product(@PathVariable Long id);
	
}
