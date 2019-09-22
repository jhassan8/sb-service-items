package com.app.items.models;

import com.app.commons.models.entity.Product;

public class Item {

	private Product product;
	private Integer count;

	public Item() {
	}

	public Item(Product product, Integer count) {
		this.product = product;
		this.count = count;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	public Double getTotal() {
		return this.product.getPrice() * this.count.doubleValue();
	}

}
