package com.fashionstore.dao.impl;

import com.fashionstore.dao.OrderDAO;
import com.fashionstore.model.Order;
import com.fashionstore.model.OrderItem;
import com.fashionstore.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {

	@Override
	public int placeOrder(Order order) {
		String sql = "INSERT INTO orders (user_id, total_amount, payment_method, order_status, "
				+ "delivery_name, delivery_phone, delivery_address_line1, delivery_address_line2, "
				+ "delivery_city, delivery_state, delivery_pincode, delivery_country) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setInt(1, order.getUserId());
			ps.setDouble(2, order.getTotalAmount());
			ps.setString(3, order.getPaymentMethod());
			ps.setString(4, order.getOrderStatus());

			ps.setString(5, order.getDeliveryName());
			ps.setString(6, order.getDeliveryPhone());
			ps.setString(7, order.getDeliveryAddressLine1());
			ps.setString(8, order.getDeliveryAddressLine2());
			ps.setString(9, order.getDeliveryCity());
			ps.setString(10, order.getDeliveryState());
			ps.setString(11, order.getDeliveryPincode());
			ps.setString(12, order.getDeliveryCountry());

			int affectedRows = ps.executeUpdate();

			if (affectedRows > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					return rs.getInt(1); // return order_id
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	@Override
	public Order getOrderById(int orderId) {
		String sql = "SELECT * FROM orders WHERE order_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, orderId);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return extractOrder(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Order> getOrdersByUserId(int userId) {
		List<Order> list = new ArrayList<>();
		String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(extractOrder(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean updateOrderStatus(int orderId, String orderStatus) {
		String sql = "UPDATE orders SET order_status = ? WHERE order_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, orderStatus);
			ps.setInt(2, orderId);

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private Order extractOrder(ResultSet rs) throws SQLException {
		Order o = new Order();

		o.setOrderId(rs.getInt("order_id"));
		o.setUserId(rs.getInt("user_id"));
		o.setOrderDate(rs.getTimestamp("order_date"));
		o.setTotalAmount(rs.getDouble("total_amount"));
		o.setPaymentMethod(rs.getString("payment_method"));
		o.setOrderStatus(rs.getString("order_status"));

		o.setDeliveryName(rs.getString("delivery_name"));
		o.setDeliveryPhone(rs.getString("delivery_phone"));
		o.setDeliveryAddressLine1(rs.getString("delivery_address_line1"));
		o.setDeliveryAddressLine2(rs.getString("delivery_address_line2"));
		o.setDeliveryCity(rs.getString("delivery_city"));
		o.setDeliveryState(rs.getString("delivery_state"));
		o.setDeliveryPincode(rs.getString("delivery_pincode"));
		o.setDeliveryCountry(rs.getString("delivery_country"));

		o.setCreatedAt(rs.getTimestamp("created_at"));
		o.setUpdatedAt(rs.getTimestamp("updated_at"));

		return o;
	}

	@Override
	public int createOrder(Order order) {
		int orderId = 0;

		try (Connection conn = DBConnection.getConnection()) {

			String sql = "INSERT INTO orders(user_id, total_amount, status) VALUES (?, ?, ?)";

			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, order.getUserId());
			ps.setDouble(2, order.getTotalAmount());
			ps.setString(3, order.getOrderStatus());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				orderId = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return orderId;
	}

	@Override
	public void addOrderItem(OrderItem item) {

		try (Connection conn = DBConnection.getConnection()) {

			String sql = "INSERT INTO order_items(order_id, variant_id, quantity, price, subtotal) VALUES (?, ?, ?, ?, ?)";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, item.getOrderId());
			ps.setInt(2, item.getVariantId());
			ps.setInt(3, item.getQuantity());
			ps.setDouble(4, item.getPrice());
			ps.setDouble(5, item.getSubtotal());

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<OrderItem> getOrderItemsByOrderId(int orderId) {

		List<OrderItem> list = new ArrayList<>();

		try (Connection conn = DBConnection.getConnection()) {

			String sql = "SELECT oi.*, p.product_id, p.product_name, p.image_url " + "FROM order_items oi "
					+ "JOIN product_variants pv ON oi.variant_id = pv.variant_id "
					+ "JOIN products p ON pv.product_id = p.product_id " + "WHERE oi.order_id = ?";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, orderId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				OrderItem oi = new OrderItem();

				oi.setOrderId(rs.getInt("order_id"));
				oi.setVariantId(rs.getInt("variant_id"));
				oi.setProductId(rs.getInt("product_id"));
				oi.setQuantity(rs.getInt("quantity"));
				oi.setPrice(rs.getDouble("price"));
				oi.setSubtotal(rs.getDouble("subtotal"));

				// ✅ FIXED
				oi.setProductName(rs.getString("product_name"));
				oi.setImageUrl(rs.getString("image_url"));

				list.add(oi);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public int getOrderCountByUserId(int userId) {
		String sql = "SELECT COUNT(*) FROM orders WHERE user_id = ? AND order_status != 'Cancelled'";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
