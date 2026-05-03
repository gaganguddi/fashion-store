package com.fashionstore.dao.impl;

import com.fashionstore.dao.CategoryDAO;
import com.fashionstore.model.Category;
import com.fashionstore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {

	@Override
	public boolean addCategory(Category category) {
		String sql = "INSERT INTO categories (category_name, description) VALUES (?, ?)";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, category.getCategoryName());
			ps.setString(2, category.getDescription());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Category getCategoryById(int categoryId) {
		String sql = "SELECT * FROM categories WHERE category_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, categoryId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return extractCategory(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Category getCategoryByName(String categoryName) {
		String sql = "SELECT * FROM categories WHERE category_name = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, categoryName);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return extractCategory(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Category> getAllCategories() {
		List<Category> categories = new ArrayList<>();
		String sql = "SELECT * FROM categories ORDER BY category_name";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				categories.add(extractCategory(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return categories;
	}

	@Override
	public boolean updateCategory(Category category) {
		String sql = "UPDATE categories SET category_name = ?, description = ? WHERE category_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, category.getCategoryName());
			ps.setString(2, category.getDescription());
			ps.setInt(3, category.getCategoryId());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteCategory(int categoryId) {
		String sql = "DELETE FROM categories WHERE category_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, categoryId);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private Category extractCategory(ResultSet rs) throws SQLException {
		Category category = new Category();
		category.setCategoryId(rs.getInt("category_id"));
		category.setCategoryName(rs.getString("category_name"));
		category.setDescription(rs.getString("description"));
		return category;
	}
}