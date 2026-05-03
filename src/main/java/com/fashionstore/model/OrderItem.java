package com.fashionstore.model;

public class OrderItem {

	private int orderId;
	private int productId;
	private int variantId; // ✅ important for size/variant
	private int quantity;
	private double price;
	private double subtotal;

	private String productName;
	private String imageUrl;

	// ===================== GETTERS & SETTERS =====================

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getVariantId() {
		return variantId;
	}

	public void setVariantId(int variantId) {
		this.variantId = variantId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
		calculateSubtotal(); // ✅ auto update
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
		calculateSubtotal(); // ✅ auto update
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	// ===================== HELPER =====================

	private void calculateSubtotal() {
		this.subtotal = this.price * this.quantity;
	}
}