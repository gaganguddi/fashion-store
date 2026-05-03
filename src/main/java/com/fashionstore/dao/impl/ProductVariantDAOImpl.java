package com.fashionstore.dao.impl;

import com.fashionstore.dao.ProductVariantDAO;
import com.fashionstore.model.ProductVariant;
import com.fashionstore.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductVariantDAOImpl implements ProductVariantDAO {

	@Override
	public boolean addVariant(ProductVariant variant) {
		String sql = "INSERT INTO product_variants (product_id, size, stock_quantity, is_available) VALUES (?, ?, ?, ?)";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, variant.getProductId());
			ps.setString(2, variant.getSize());
			ps.setInt(3, variant.getStockQuantity());
			ps.setBoolean(4, variant.isAvailable());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ProductVariant getVariantById(int variantId) {
		String sql = "SELECT * FROM product_variants WHERE variant_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, variantId);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return extractVariant(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ProductVariant getVariantByProductAndSize(int productId, String size) {
		String sql = "SELECT * FROM product_variants WHERE product_id = ? AND size = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, productId);
			ps.setString(2, size);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return extractVariant(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ProductVariant> getVariantsByProductId(int productId) {
		List<ProductVariant> list = new ArrayList<>();
		String sql = "SELECT * FROM product_variants WHERE product_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, productId);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(extractVariant(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean updateVariant(ProductVariant variant) {
		String sql = "UPDATE product_variants SET size = ?, stock_quantity = ?, is_available = ? WHERE variant_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, variant.getSize());
			ps.setInt(2, variant.getStockQuantity());
			ps.setBoolean(3, variant.isAvailable());
			ps.setInt(4, variant.getVariantId());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updateStock(int variantId, int quantity) {
		String sql = "UPDATE product_variants SET stock_quantity = ? WHERE variant_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, quantity);
			ps.setInt(2, variantId);

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean decreaseStock(int variantId, int quantity) {
		String sql = "UPDATE product_variants SET stock_quantity = stock_quantity - ? WHERE variant_id = ? AND stock_quantity >= ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, quantity);
			ps.setInt(2, variantId);
			ps.setInt(3, quantity);

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean increaseStock(int variantId, int quantity) {
		String sql = "UPDATE product_variants SET stock_quantity = stock_quantity + ? WHERE variant_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, quantity);
			ps.setInt(2, variantId);

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteVariant(int variantId) {
		String sql = "DELETE FROM product_variants WHERE variant_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, variantId);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private ProductVariant extractVariant(ResultSet rs) throws SQLException {
		ProductVariant v = new ProductVariant();
		v.setVariantId(rs.getInt("variant_id"));
		v.setProductId(rs.getInt("product_id"));
		v.setSize(rs.getString("size"));
		v.setStockQuantity(rs.getInt("stock_quantity"));
		v.setAvailable(rs.getBoolean("is_available"));
		return v;
	}
}