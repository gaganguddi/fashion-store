package com.fashionstore.dao.impl;

import com.fashionstore.dao.UserDAO;
import com.fashionstore.model.User;
import com.fashionstore.util.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

	@Override
	public boolean registerUser(User user) {
		String sql = "INSERT INTO users (full_name, email, phone, password, address_line1, address_line2, city, state, pincode, country) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			// Hash the password before storing
			String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

			ps.setString(1, user.getFullName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPhone());
			ps.setString(4, hashedPassword);
			ps.setString(5, user.getAddressLine1());
			ps.setString(6, user.getAddressLine2());
			ps.setString(7, user.getCity());
			ps.setString(8, user.getState());
			ps.setString(9, user.getPincode());
			ps.setString(10, user.getCountry());

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public User loginUser(String email, String password) {
		// Fetch user by email first to check password hash
		String sql = "SELECT * FROM users WHERE email = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				String storedPassword = rs.getString("password");
				
				// Verify password (supports BCrypt and legacy plain text)
				boolean passwordMatch = false;
				if (storedPassword.startsWith("$2a$")) { // BCrypt hash format
					passwordMatch = BCrypt.checkpw(password, storedPassword);
				} else {
					passwordMatch = password.equals(storedPassword);
				}

				if (passwordMatch) {
					return extractUser(rs);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User getUserById(int userId) {
		String sql = "SELECT * FROM users WHERE user_id = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return extractUser(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User getUserByEmail(String email) {
		String sql = "SELECT * FROM users WHERE email = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return extractUser(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User getUserByPhone(String phone) {
		String sql = "SELECT * FROM users WHERE phone = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, phone);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return extractUser(rs);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean updateUser(User user) {
		String sql = "UPDATE users SET full_name=?, phone=?, address_line1=?, address_line2=?, city=?, state=?, pincode=?, country=? WHERE user_id=?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, user.getFullName());
			ps.setString(2, user.getPhone());
			ps.setString(3, user.getAddressLine1());
			ps.setString(4, user.getAddressLine2());
			ps.setString(5, user.getCity());
			ps.setString(6, user.getState());
			ps.setString(7, user.getPincode());
			ps.setString(8, user.getCountry());
			ps.setInt(9, user.getUserId());

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean updatePassword(int userId, String newPassword) {
		String sql = "UPDATE users SET password=? WHERE user_id=?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			// Hash the new password
			String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
			ps.setString(1, hashedPassword);
			ps.setInt(2, userId);

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean emailExists(String email) {
		String sql = "SELECT user_id FROM users WHERE email = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();

			return rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean phoneExists(String phone) {
		String sql = "SELECT user_id FROM users WHERE phone = ?";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, phone);
			ResultSet rs = ps.executeQuery();

			return rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> list = new ArrayList<>();
		String sql = "SELECT * FROM users";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				list.add(extractUser(rs));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	// Helper method
	private User extractUser(ResultSet rs) throws SQLException {
		User user = new User();

		user.setUserId(rs.getInt("user_id"));
		user.setFullName(rs.getString("full_name"));
		user.setEmail(rs.getString("email"));
		user.setPhone(rs.getString("phone"));
		user.setPassword(rs.getString("password"));
		user.setAddressLine1(rs.getString("address_line1"));
		user.setAddressLine2(rs.getString("address_line2"));
		user.setCity(rs.getString("city"));
		user.setState(rs.getString("state"));
		user.setPincode(rs.getString("pincode"));
		user.setCountry(rs.getString("country"));
		user.setCreatedAt(rs.getTimestamp("created_at"));

		return user;
	}
}