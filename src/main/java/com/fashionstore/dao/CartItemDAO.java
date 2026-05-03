package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.CartItem;

public interface CartItemDAO {

	boolean addCartItem(CartItem cartItem);

	CartItem getCartItem(int cartId, int variantId);

	List<CartItem> getCartItemsByCartId(int cartId);

	boolean updateCartItemQuantity(int cartItemId, int quantity);

	boolean removeCartItem(int cartItemId);

	boolean removeCartItemByVariant(int cartId, int variantId);

	boolean clearCartItems(int cartId);

	int getCartItemCount(int cartId);
}