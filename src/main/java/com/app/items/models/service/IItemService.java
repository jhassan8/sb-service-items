package com.app.items.models.service;

import java.util.List;

import com.app.items.models.Item;
import com.app.items.models.Product;

public interface IItemService {

	public List<Item> findAll();
	public Item findById(Long id, Integer count);
	public Product createProduct(Product product);
	public Product updateProduct(Product product, Long id);
	public void deleteProduct(Long id);
}
