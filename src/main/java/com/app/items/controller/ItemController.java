package com.app.items.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.app.items.models.Item;
import com.app.items.models.Product;
import com.app.items.models.service.IItemService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RefreshScope
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
	
	@PostMapping("/new-product")
	@ResponseStatus(HttpStatus.CREATED)
	public Product creatProduct(@RequestBody Product product) {
		return iItemService.createProduct(product);
	}
	
	@PutMapping("/update-product/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Product updateProduct(@RequestBody Product product, @PathVariable Long id) {
		return iItemService.updateProduct(product, id);
	}
	
	@DeleteMapping("/delete-product/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT) 
	public void deleteProduct(@PathVariable Long id){
		iItemService.deleteProduct(id);
	}
}
