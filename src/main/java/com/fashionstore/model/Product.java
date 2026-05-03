package com.fashionstore.model;

import java.sql.Timestamp;

public class Product {

	private int productId;
	private int categoryId;

	private String productName;
	private String brand;
	private String description;

	private double price;
	private double discountPercent;

	private String imageUrl;
	private boolean isActive;

	private Timestamp createdAt;

	// Default Constructor
	public Product() {
	}

	// Parameterized Constructor
	public Product(int productId, int categoryId, String productName, String brand, String description, double price,
			double discountPercent, String imageUrl, boolean isActive, Timestamp createdAt) {
		this.productId = productId;
		this.categoryId = categoryId;
		this.productName = productName;
		this.brand = brand;
		this.description = description;
		this.price = price;
		this.discountPercent = discountPercent;
		this.imageUrl = imageUrl;
		this.isActive = isActive;
		this.createdAt = createdAt;
	}

	// Getters and Setters

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(double discountPercent) {
		this.discountPercent = discountPercent;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
}