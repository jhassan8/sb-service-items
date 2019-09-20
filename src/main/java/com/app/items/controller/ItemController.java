package com.app.items.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.app.items.models.Item;
import com.app.items.models.service.IItemService;

@RestController
public class ItemController {
	
	private IItemService iItemService;
	
	@Autowired
	//@Qualifier("serviceFeign")
	public ItemController(@Qualifier("serviceFeign") IItemService iItemService) {
		this.iItemService = iItemService;
	}
	
	@GetMapping("items")
	public List<Item> items() {
		return iItemService.findAll();
	}
	
	@GetMapping("item/{id}/count/{count}")
	public Item item(@PathVariable Long id, @PathVariable Integer count) {
		return iItemService.findById(id, count);
	}

}
