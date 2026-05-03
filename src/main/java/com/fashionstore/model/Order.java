package com.fashionstore.model;

import java.sql.Timestamp;
import java.util.List;

public class Order {

	private int orderId;
	private int userId;

	private Timestamp orderDate;
	private double totalAmount;

	private String paymentMethod;
	private String orderStatus;

	private String deliveryName;
	private String deliveryPhone;

	private String deliveryAddressLine1;
	private String deliveryAddressLine2;
	private String deliveryCity;
	private String deliveryState;
	private String deliveryPincode;
	private String deliveryCountry;

	private Timestamp createdAt;
	private Timestamp updatedAt;

	// Default Constructor
	public Order() {
	}

	// Parameterized Constructor
	public Order(int orderId, int userId, Timestamp orderDate, double totalAmount, String paymentMethod,
			String orderStatus, String deliveryName, String deliveryPhone, String deliveryAddressLine1,
			String deliveryAddressLine2, String deliveryCity, String deliveryState, String deliveryPincode,
			String deliveryCountry, Timestamp createdAt, Timestamp updatedAt) {

		this.orderId = orderId;
		this.userId = userId;
		this.orderDate = orderDate;
		this.totalAmount = totalAmount;
		this.paymentMethod = paymentMethod;
		this.orderStatus = orderStatus;
		this.deliveryName = deliveryName;
		this.deliveryPhone = deliveryPhone;
		this.deliveryAddressLine1 = deliveryAddressLine1;
		this.deliveryAddressLine2 = deliveryAddressLine2;
		this.deliveryCity = deliveryCity;
		this.deliveryState = deliveryState;
		this.deliveryPincode = deliveryPincode;
		this.deliveryCountry = deliveryCountry;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	// Getters and Setters

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Timestamp getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getDeliveryName() {
		return deliveryName;
	}

	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}

	public String getDeliveryPhone() {
		return deliveryPhone;
	}

	public void setDeliveryPhone(String deliveryPhone) {
		this.deliveryPhone = deliveryPhone;
	}

	public String getDeliveryAddressLine1() {
		return deliveryAddressLine1;
	}

	public void setDeliveryAddressLine1(String deliveryAddressLine1) {
		this.deliveryAddressLine1 = deliveryAddressLine1;
	}

	public String getDeliveryAddressLine2() {
		return deliveryAddressLine2;
	}

	public void setDeliveryAddressLine2(String deliveryAddressLine2) {
		this.deliveryAddressLine2 = deliveryAddressLine2;
	}

	public String getDeliveryCity() {
		return deliveryCity;
	}

	public void setDeliveryCity(String deliveryCity) {
		this.deliveryCity = deliveryCity;
	}

	public String getDeliveryState() {
		return deliveryState;
	}

	public void setDeliveryState(String deliveryState) {
		this.deliveryState = deliveryState;
	}

	public String getDeliveryPincode() {
		return deliveryPincode;
	}

	public void setDeliveryPincode(String deliveryPincode) {
		this.deliveryPincode = deliveryPincode;
	}

	public String getDeliveryCountry() {
		return deliveryCountry;
	}

	public void setDeliveryCountry(String deliveryCountry) {
		this.deliveryCountry = deliveryCountry;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	private List<OrderItem> items;

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
}