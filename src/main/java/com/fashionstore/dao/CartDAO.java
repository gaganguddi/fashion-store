package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.Cart;
import com.fashionstore.model.CartItem;

public interface CartDAO {

	boolean createCart(int userId);

	Cart getCartByUserId(int userId);

	boolean cartExists(int userId);

	boolean clearCart(int cartId);

	boolean deleteCart(int cartId);

	boolean addToCart(int userId, int variantId, int quantity);

	List<CartItem> getCartItemsByUserId(int userId);

	// 🔥 NEW
	boolean updateQuantity(int cartItemId, int quantity);

	boolean removeItem(int cartItemId);
}