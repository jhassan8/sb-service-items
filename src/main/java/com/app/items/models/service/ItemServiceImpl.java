package com.app.items.models.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.items.models.Item;
import com.app.commons.models.entity.Product;

@Service("serviceRestTemplate")
//@Primary
public class ItemServiceImpl implements IItemService {
	
	private RestTemplate clientRest;
	
	@Autowired
	public ItemServiceImpl(RestTemplate clientRest) {
		this.clientRest = clientRest;
	}
	
	@Override
	public List<Item> findAll() {
		List<Product> products = Arrays.asList(clientRest.getForObject("http://service-products/all", Product[].class));
		return products.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer count) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		Product product = clientRest.getForObject("http://service-products/view/{id}", Product.class, pathVariables);
		return new Item(product, count);
	}

	@Override
	public Product createProduct(Product product) {
		HttpEntity<Product> body = new HttpEntity<Product>(product);
		ResponseEntity<Product> response = clientRest.exchange("http://service-products/new", HttpMethod.POST, body, Product.class);
		return response.getBody();
	}

	@Override
	public Product updateProduct(Product product, Long id) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		HttpEntity<Product> body = new HttpEntity<Product>(product);
		ResponseEntity<Product> response = clientRest.exchange("http://service-products/update/{id}", HttpMethod.PUT, body, Product.class, pathVariables);
		return response.getBody();
	}

	@Override
	public void deleteProduct(Long id) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		clientRest.delete("http://service-products/delete/{id}", pathVariables);
	}

}
