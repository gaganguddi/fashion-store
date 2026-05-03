package com.fashionstore.dao.impl;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.model.Product;
import com.fashionstore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

	@Override
	public boolean addProduct(Product product) {
		String sql = "INSERT INTO products (category_id, product_name, brand, description, price, discount_percent, image_url, is_active) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, product.getCategoryId());
			ps.setString(2, product.getProductName());
			ps.setString(3, product.getBrand());
			ps.setString(4, product.getDescription());
			ps.setDouble(5, product.getPrice());
			ps.setDouble(6, product.getDiscountPercent());
			ps.setString(7, product.getImageUrl());
			ps.setBoolean(8, product.isActive());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Product getProductById(int productId) {
		String sql = "SELECT * FROM products WHERE product_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, productId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return extractProduct(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Product> getAllProducts() {
		List<Product> products = new ArrayList<>();
		String sql = "SELECT * FROM products ORDER BY created_at DESC";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				products.add(extractProduct(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return products;
	}

	@Override
	public List<Product> getProductsByCategory(int categoryId) {
		List<Product> products = new ArrayList<>();
		String sql = "SELECT * FROM products WHERE category_id = ? ORDER BY created_at DESC";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, categoryId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					products.add(extractProduct(rs));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return products;
	}

	@Override
	public List<Product> searchProducts(String keyword) {
		List<Product> products = new ArrayList<>();
		String sql = "SELECT * FROM products " + "WHERE product_name LIKE ? OR brand LIKE ? OR description LIKE ? "
				+ "ORDER BY created_at DESC";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			String searchText = "%" + keyword + "%";
			ps.setString(1, searchText);
			ps.setString(2, searchText);
			ps.setString(3, searchText);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					products.add(extractProduct(rs));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return products;
	}

	@Override
	public List<Product> filterProducts(int categoryId, String size, Double minPrice, Double maxPrice, String sortBy) {
		List<Product> products = new ArrayList<>();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT DISTINCT p.* FROM products p ");

		boolean joinVariant = size != null && !size.trim().isEmpty();
		if (joinVariant) {
			sql.append("INNER JOIN product_variants pv ON p.product_id = pv.product_id ");
		}

		sql.append("WHERE p.is_active = TRUE ");

		if (categoryId > 0) {
			sql.append("AND p.category_id = ? ");
		}

		if (joinVariant) {
			sql.append("AND pv.size = ? ");
		}

		if (minPrice != null) {
			sql.append("AND p.price >= ? ");
		}

		if (maxPrice != null) {
			sql.append("AND p.price <= ? ");
		}

		// Safe sorting
		if ("price_asc".equalsIgnoreCase(sortBy)) {
			sql.append("ORDER BY p.price ASC ");
		} else if ("price_desc".equalsIgnoreCase(sortBy)) {
			sql.append("ORDER BY p.price DESC ");
		} else if ("newest".equalsIgnoreCase(sortBy)) {
			sql.append("ORDER BY p.created_at DESC ");
		} else {
			sql.append("ORDER BY p.created_at DESC ");
		}

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {
			int index = 1;

			if (categoryId > 0) {
				ps.setInt(index++, categoryId);
			}

			if (joinVariant) {
				ps.setString(index++, size.trim());
			}

			if (minPrice != null) {
				ps.setDouble(index++, minPrice);
			}

			if (maxPrice != null) {
				ps.setDouble(index++, maxPrice);
			}

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					products.add(extractProduct(rs));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return products;
	}

	@Override
	public boolean updateProduct(Product product) {
		String sql = "UPDATE products SET category_id = ?, product_name = ?, brand = ?, description = ?, price = ?, "
				+ "discount_percent = ?, image_url = ?, is_active = ? WHERE product_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, product.getCategoryId());
			ps.setString(2, product.getProductName());
			ps.setString(3, product.getBrand());
			ps.setString(4, product.getDescription());
			ps.setDouble(5, product.getPrice());
			ps.setDouble(6, product.getDiscountPercent());
			ps.setString(7, product.getImageUrl());
			ps.setBoolean(8, product.isActive());
			ps.setInt(9, product.getProductId());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteProduct(int productId) {
		String sql = "DELETE FROM products WHERE product_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, productId);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private Product extractProduct(ResultSet rs) throws SQLException {
		Product product = new Product();
		product.setProductId(rs.getInt("product_id"));
		product.setCategoryId(rs.getInt("category_id"));
		product.setProductName(rs.getString("product_name"));
		product.setBrand(rs.getString("brand"));
		product.setDescription(rs.getString("description"));
		product.setPrice(rs.getDouble("price"));
		product.setDiscountPercent(rs.getDouble("discount_percent"));
		product.setImageUrl(rs.getString("image_url"));
		product.setActive(rs.getBoolean("is_active"));
		product.setCreatedAt(rs.getTimestamp("created_at"));
		return product;
	}

	@Override
	public List<Product> getRelatedProducts(int categoryId, int productId) {

		List<Product> list = new ArrayList<>();

		String sql = "SELECT * FROM products WHERE category_id=? AND product_id!=? LIMIT 4";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, categoryId);
			ps.setInt(2, productId);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Product p = new Product();

				p.setProductId(rs.getInt("product_id"));
				p.setProductName(rs.getString("product_name"));
				p.setPrice(rs.getDouble("price"));
				p.setImageUrl(rs.getString("image_url"));

				list.add(p);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}