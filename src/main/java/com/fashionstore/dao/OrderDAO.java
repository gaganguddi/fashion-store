package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.Order;
import com.fashionstore.model.OrderItem;

public interface OrderDAO {

	int placeOrder(Order order);
	Order getOrderById(int orderId);
	List<Order> getOrdersByUserId(int userId);
	boolean updateOrderStatus(int orderId, String orderStatus);
	int createOrder(Order order);
	void addOrderItem(OrderItem item);
	List<OrderItem> getOrderItemsByOrderId(int orderId);
	int getOrderCountByUserId(int userId);
}

// fix above error that it still have some error so it and AI is alrady give 