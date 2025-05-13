package com.ArtisanRoots7.model;

import java.time.LocalDate;

public class ProductModel {
	String productId;
	String productName;
	float price;
	int quantity;
	LocalDate createdDate;
	int categoryId;
	int totalSales;
	String productStatus;
	
	
	public ProductModel(String productId, String productName, float price, int quantity, LocalDate createdDate,
			int categoryId, int totalSales, String productStatus) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
		this.createdDate = createdDate;
		this.totalSales=totalSales;
		this.productStatus=productStatus;
		this.categoryId = categoryId;
	}
	public String getProductId() {
		return productId;
	}
	public String getProductName() {
		return productName;
	}
	public float getPrice() {
		return price;
	}
	public int getQuantity() {
		return quantity;
	}
	public LocalDate getCreatedDate() {
		return createdDate;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getTotalSales() {
		return totalSales;
	}
	public String getProductStatus() {
		return productStatus;
	}
	public void setTotalSales(int totalSales) {
		this.totalSales = totalSales;
	}
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}

}
