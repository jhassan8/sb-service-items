package com.app.items.models.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.items.models.Item;
import com.app.items.models.Product;

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
		List<Product> products = Arrays.asList(clientRest.getForObject("http://localhost:8001/products", Product[].class));
		return products.stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer count) {
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		Product product = clientRest.getForObject("http://localhost:8001/product/{id}", Product.class, pathVariables);
		return new Item(product, count);
	}

}
