package com.fashionstore.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseSelectTest {

	public static void main(String[] args) {

		try (Connection conn = DBConnection.getConnection()) {
			if (conn == null) {
				System.out.println("Connection failed");
				return;
			}

			System.out.println("=== USERS ===");
			printQuery(conn, "SELECT user_id, full_name, email, phone, city FROM users");

			System.out.println("\n=== CATEGORIES ===");
			printQuery(conn, "SELECT category_id, category_name, description FROM categories");

			System.out.println("\n=== PRODUCTS ===");
			printQuery(conn,
					"SELECT p.product_id, p.product_name, c.category_name, p.brand, p.price, p.discount_percent "
							+ "FROM products p JOIN categories c ON p.category_id = c.category_id");

			System.out.println("\n=== PRODUCT VARIANTS ===");
			printQuery(conn, "SELECT pv.variant_id, p.product_name, pv.size, pv.stock_quantity, pv.is_available "
					+ "FROM product_variants pv JOIN products p ON pv.product_id = p.product_id");

			System.out.println("\n=== CART ITEMS ===");
			printQuery(conn,
					"SELECT ci.cart_item_id, ci.cart_id, p.product_name, pv.size, ci.quantity, ci.unit_price "
							+ "FROM cart_items ci " + "JOIN product_variants pv ON ci.variant_id = pv.variant_id "
							+ "JOIN products p ON pv.product_id = p.product_id");

			System.out.println("\n=== ORDERS ===");
			printQuery(conn, "SELECT order_id, user_id, total_amount, payment_method, order_status FROM orders");

			System.out.println("\n=== ORDER ITEMS ===");
			printQuery(conn, "SELECT oi.order_item_id, oi.order_id, oi.variant_id, oi.quantity, oi.price, oi.subtotal "
					+ "FROM order_items oi");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void printQuery(Connection conn, String sql) {
		try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

			int columnCount = rs.getMetaData().getColumnCount();

			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					System.out.print(rs.getMetaData().getColumnLabel(i) + "=" + rs.getString(i) + "  ");
				}
				System.out.println();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}