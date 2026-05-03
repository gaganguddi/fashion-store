package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.User;

public interface UserDAO {

	boolean registerUser(User user);

	User loginUser(String email, String password);

	User getUserById(int userId);

	User getUserByEmail(String email);

	User getUserByPhone(String phone);

	boolean updateUser(User user);

	boolean updatePassword(int userId, String newPassword);

	boolean emailExists(String email);

	boolean phoneExists(String phone);

	List<User> getAllUsers();
}