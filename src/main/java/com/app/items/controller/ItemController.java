package com.app.items.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.app.items.models.Item;
import com.app.items.models.Product;
import com.app.items.models.service.IItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class ItemController {
		
	//@Qualifier("serviceRestTemplate")
	private IItemService iItemService;
	
	@Value("${server.port}")
	private String portConfig;
	
	@Value("${env}")
	private String envConfig;
	
	@Autowired
	public ItemController(@Qualifier("serviceFeign") IItemService iItemService) {
		this.iItemService = iItemService;
	}
	
	@GetMapping("/all")
	public List<Item> items() {
		return iItemService.findAll();
	}
	
	@HystrixCommand(fallbackMethod = "alternMethod")
	@GetMapping("/view/{id}/count/{count}")
	public Item item(@PathVariable Long id, @PathVariable Integer count) {
		return iItemService.findById(id, count);
	}

	public Item alternMethod(Long id, Integer count) {
		Item item = new Item();
		Product product =  new Product();
		
		item.setCount(count);
		product.setId(id);
		product.setName("altern name");
		product.setPrice(500.00);
		item.setProduct(product);
		
		return item;
	}
	
	@GetMapping("/get-config")
	public ResponseEntity<?> getConfig() {
				
		Map<String, String> returned = new HashMap<String, String>();
		
		returned.put("env", envConfig);
		returned.put("port", portConfig);
		
		return new ResponseEntity<Map<String, String>>(returned, HttpStatus.OK);
	}
}
