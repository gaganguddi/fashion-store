package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.OrderItem;

public interface OrderItemDAO {

	boolean addOrderItem(OrderItem orderItem);

	List<OrderItem> getOrderItemsByOrderId(int orderId);

	boolean deleteOrderItemsByOrderId(int orderId);
}