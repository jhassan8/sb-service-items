package com.app.items.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.items.client.ProductClientRest;
import com.app.items.models.Item;

@Service("serviceFeign")
//@Primary
public class ItemServiceFeign implements IItemService {

	private ProductClientRest productClientRest;
	
	@Autowired
	public ItemServiceFeign(ProductClientRest productClientRest) {
		this.productClientRest = productClientRest;
	}
	
	@Override
	public List<Item> findAll() {
		return productClientRest.products().stream().map(p -> new Item(p, 1)).collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer count) {
		return new Item(productClientRest.product(id), count);
	}

}
