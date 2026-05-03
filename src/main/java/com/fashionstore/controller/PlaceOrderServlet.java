package com.fashionstore.controller;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.dao.OrderDAO;
import com.fashionstore.dao.impl.CartDAOImpl;
import com.fashionstore.dao.impl.CartItemDAOImpl;
import com.fashionstore.dao.impl.OrderDAOImpl;
import com.fashionstore.model.Cart;
import com.fashionstore.model.CartItem;
import com.fashionstore.model.Order;
import com.fashionstore.model.OrderItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/place-order")
public class PlaceOrderServlet extends HttpServlet {

	private final OrderDAO orderDAO = new OrderDAOImpl();
	private final CartDAO cartDAO = new CartDAOImpl();
	private final CartItemDAO cartItemDAO = new CartItemDAOImpl();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ✅ STEP 1: Check login
		com.fashionstore.model.User user = (com.fashionstore.model.User) request.getSession().getAttribute("loggedInUser");

		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		int userId = user.getUserId();

		// ✅ STEP 2: Get cart
		Cart cart = cartDAO.getCartByUserId(userId);

		if (cart == null) {
			response.sendRedirect(request.getContextPath() + "/cart");
			return;
		}

		// ✅ STEP 3: Get cart items
		List<CartItem> items = cartItemDAO.getCartItemsByCartId(cart.getCartId());

		if (items == null || items.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/cart");
			return;
		}

		// ✅ STEP 4: Calculate total
		double total = 0;
		for (CartItem item : items) {
			total += item.getSubtotal();
		}

		// ✅ STEP 5: Create Order
		Order order = new Order();
		order.setUserId(userId);
		order.setTotalAmount(total);
		order.setOrderStatus("PLACED");
		order.setPaymentMethod("COD");

		order.setDeliveryName(user.getFullName());
		order.setDeliveryPhone(user.getPhone());
		order.setDeliveryAddressLine1(user.getAddressLine1());
		order.setDeliveryAddressLine2(user.getAddressLine2());
		order.setDeliveryCity(user.getCity());
		order.setDeliveryState(user.getState());
		order.setDeliveryPincode(user.getPincode());
		order.setDeliveryCountry(user.getCountry());

		int orderId = orderDAO.placeOrder(order);

		if (orderId > 0) {
			// ✅ STEP 6: Save Order Items
			for (CartItem item : items) {
				OrderItem oi = new OrderItem();
	
				oi.setOrderId(orderId);
				oi.setVariantId(item.getVariantId());
				oi.setQuantity(item.getQuantity());
				oi.setPrice(item.getUnitPrice());
				oi.setSubtotal(item.getSubtotal());
	
				orderDAO.addOrderItem(oi);
			}
		}

		// ✅ STEP 7: Clear cart
		cartItemDAO.clearCartItems(cart.getCartId());

		// ✅ STEP 8: Redirect (ONLY ONE RESPONSE)
		response.sendRedirect(request.getContextPath() + "/order-success");
	}
}