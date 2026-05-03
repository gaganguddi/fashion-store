package com.fashionstore.dao.impl;

import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.model.CartItem;
import com.fashionstore.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartItemDAOImpl implements CartItemDAO {

	@Override
	public boolean addCartItem(CartItem cartItem) {

		String sql = "INSERT INTO cart_items (cart_id, variant_id, quantity, unit_price) VALUES (?, ?, ?, ?)";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, cartItem.getCartId());
			ps.setInt(2, cartItem.getVariantId());
			ps.setInt(3, cartItem.getQuantity());
			ps.setDouble(4, cartItem.getUnitPrice());

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public CartItem getCartItem(int cartId, int variantId) {

		String sql = "SELECT * FROM cart_items WHERE cart_id = ? AND variant_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, cartId);
			ps.setInt(2, variantId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return extractCartItem(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<CartItem> getCartItemsByCartId(int cartId) {

		List<CartItem> list = new ArrayList<>();

		// ✅ FIXED: Added product + size
		String sql = "SELECT ci.*, p.product_id, p.product_name, p.image_url, pv.size " + "FROM cart_items ci "
				+ "JOIN product_variants pv ON ci.variant_id = pv.variant_id "
				+ "JOIN products p ON pv.product_id = p.product_id " + "WHERE ci.cart_id = ? ORDER BY ci.added_at DESC";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, cartId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				list.add(extractCartItem(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public boolean updateCartItemQuantity(int cartItemId, int quantity) {

		String sql = "UPDATE cart_items SET quantity = ? WHERE cart_item_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, quantity);
			ps.setInt(2, cartItemId);

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean removeCartItem(int cartItemId) {

		String sql = "DELETE FROM cart_items WHERE cart_item_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, cartItemId);
			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean removeCartItemByVariant(int cartId, int variantId) {

		String sql = "DELETE FROM cart_items WHERE cart_id = ? AND variant_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, cartId);
			ps.setInt(2, variantId);

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean clearCartItems(int cartId) {

		String sql = "DELETE FROM cart_items WHERE cart_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, cartId);
			ps.executeUpdate();

			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public int getCartItemCount(int cartId) {

		String sql = "SELECT COUNT(*) FROM cart_items WHERE cart_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, cartId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	// ================= CORE FIX =================
	private CartItem extractCartItem(ResultSet rs) throws SQLException {

		CartItem item = new CartItem();

		item.setCartItemId(rs.getInt("cart_item_id"));
		item.setCartId(rs.getInt("cart_id"));
		item.setVariantId(rs.getInt("variant_id"));

		item.setQuantity(rs.getInt("quantity"));
		item.setUnitPrice(rs.getDouble("unit_price"));

		item.setAddedAt(rs.getTimestamp("added_at"));

		// ✅ IMPORTANT FIXES
		try {
			item.setProductId(rs.getInt("product_id"));
		} catch (Exception ignored) {
		}

		try {
			item.setProductName(rs.getString("product_name"));
		} catch (Exception ignored) {
		}

		try {
			item.setSize(rs.getString("size"));
		} catch (Exception ignored) {
		}

		try {
			item.setImageUrl(rs.getString("image_url"));
		} catch (Exception ignored) {
		}

		return item;
	}
}