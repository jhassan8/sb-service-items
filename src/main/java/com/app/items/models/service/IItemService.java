package com.app.items.models.service;

import java.util.List;

import com.app.items.models.Item;

public interface IItemService {

	public List<Item> findAll();
	public Item findById(Long id, Integer count);
}
