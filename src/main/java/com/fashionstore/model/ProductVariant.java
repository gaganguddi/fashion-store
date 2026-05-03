package com.fashionstore.model;

public class ProductVariant {

	private int variantId;
	private int productId;

	private String size;
	private int stockQuantity;
	private boolean isAvailable;

	// Default Constructor
	public ProductVariant() {
	}

	// Parameterized Constructor
	public ProductVariant(int variantId, int productId, String size, int stockQuantity, boolean isAvailable) {
		this.variantId = variantId;
		this.productId = productId;
		this.size = size;
		this.stockQuantity = stockQuantity;
		this.isAvailable = isAvailable;
	}

	// Getters and Setters

	public int getVariantId() {
		return variantId;
	}

	public void setVariantId(int variantId) {
		this.variantId = variantId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean available) {
		isAvailable = available;
	}
}