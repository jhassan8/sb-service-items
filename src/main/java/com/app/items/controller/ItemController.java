package com.app.items.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	
	@Autowired
	public ItemController(@Qualifier("serviceFeign") IItemService iItemService) {
		this.iItemService = iItemService;
	}
	
	@GetMapping("items")
	public List<Item> items() {
		return iItemService.findAll();
	}
	
	@HystrixCommand(fallbackMethod = "alternMethod")
	@GetMapping("item/{id}/count/{count}")
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
}
