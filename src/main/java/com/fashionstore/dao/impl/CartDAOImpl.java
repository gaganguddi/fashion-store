package com.fashionstore.dao.impl;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.model.Cart;
import com.fashionstore.model.CartItem;
import com.fashionstore.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAOImpl implements CartDAO {

	@Override
	public boolean createCart(int userId) {
		String sql = "INSERT INTO cart(user_id) VALUES (?)";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Cart getCartByUserId(int userId) {
		String sql = "SELECT * FROM cart WHERE user_id=?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				Cart cart = new Cart();
				cart.setCartId(rs.getInt("cart_id"));
				cart.setUserId(rs.getInt("user_id"));
				cart.setCreatedAt(rs.getTimestamp("created_at"));
				cart.setUpdatedAt(rs.getTimestamp("updated_at"));
				return cart;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean cartExists(int userId) {
		return getCartByUserId(userId) != null;
	}

	@Override
	public boolean clearCart(int cartId) {
		String sql = "DELETE FROM cart_items WHERE cart_id=?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, cartId);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteCart(int cartId) {
		String sql = "DELETE FROM cart WHERE cart_id=?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, cartId);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addToCart(int userId, int variantId, int quantity) {

		try {
			if (!cartExists(userId)) {
				createCart(userId);
			}

			Cart cart = getCartByUserId(userId);
			if (cart == null) {
				return false;
			}

			String checkSql = "SELECT cart_item_id, quantity FROM cart_items WHERE cart_id=? AND variant_id=?";
			try (Connection conn = DBConnection.getConnection(); PreparedStatement checkPs = conn.prepareStatement(checkSql)) {
				checkPs.setInt(1, cart.getCartId());
				checkPs.setInt(2, variantId);

				try (ResultSet rs = checkPs.executeQuery()) {
					if (rs.next()) {
						String updateSql = "UPDATE cart_items SET quantity = quantity + ? WHERE cart_id=? AND variant_id=?";
						try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
							ps.setInt(1, quantity);
							ps.setInt(2, cart.getCartId());
							ps.setInt(3, variantId);
							return ps.executeUpdate() > 0;
						}
					}
				}
			}

			String insertSql = "INSERT INTO cart_items(cart_id, variant_id, quantity, unit_price) "
					+ "SELECT ?, pv.variant_id, ?, p.price "
					+ "FROM product_variants pv JOIN products p ON pv.product_id = p.product_id "
					+ "WHERE pv.variant_id=?";

			try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(insertSql)) {
				ps.setInt(1, cart.getCartId());
				ps.setInt(2, quantity);
				ps.setInt(3, variantId);
				return ps.executeUpdate() > 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public List<CartItem> getCartItemsByUserId(int userId) {

		List<CartItem> list = new ArrayList<>();

		String sql = "SELECT ci.cart_item_id, ci.cart_id, ci.variant_id, ci.quantity, ci.unit_price, ci.added_at, "
				+ "p.product_id, p.product_name, p.image_url, pv.size " + "FROM cart_items ci "
				+ "JOIN cart c ON ci.cart_id = c.cart_id "
				+ "JOIN product_variants pv ON ci.variant_id = pv.variant_id "
				+ "JOIN products p ON pv.product_id = p.product_id " + "WHERE c.user_id=?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {

				CartItem item = new CartItem();

				item.setCartItemId(rs.getInt("cart_item_id"));
				item.setCartId(rs.getInt("cart_id"));
				item.setVariantId(rs.getInt("variant_id"));
				item.setProductId(rs.getInt("product_id"));
				item.setProductName(rs.getString("product_name"));
				item.setImageUrl(rs.getString("image_url"));
				item.setSize(rs.getString("size"));
				item.setQuantity(rs.getInt("quantity"));
				item.setUnitPrice(rs.getDouble("unit_price"));
				item.setAddedAt(rs.getTimestamp("added_at"));

				list.add(item);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public boolean updateQuantity(int cartItemId, int quantity) {

		String sql = "UPDATE cart_items SET quantity=? WHERE cart_item_id=?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, quantity);
			ps.setInt(2, cartItemId);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean removeItem(int cartItemId) {

		String sql = "DELETE FROM cart_items WHERE cart_item_id=?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, cartItemId);
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}