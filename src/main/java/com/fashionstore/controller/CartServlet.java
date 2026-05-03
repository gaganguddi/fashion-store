package com.fashionstore.controller;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.dao.impl.CartDAOImpl;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

	private final CartDAO cartDAO = new CartDAOImpl();

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		User user = (session != null) ? (User) session.getAttribute("loggedInUser") : null;

		if (user == null) {
			response.sendRedirect("login");
			return;
		}

		String action = request.getParameter("action");

		if ("add".equals(action)) {
			int variantId = Integer.parseInt(request.getParameter("variantId"));
			int quantity = Integer.parseInt(request.getParameter("quantity"));

			cartDAO.addToCart(user.getUserId(), variantId, quantity);

		} else if ("update".equals(action)) {
			int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
			int quantity = Integer.parseInt(request.getParameter("quantity"));

			cartDAO.updateQuantity(cartItemId, quantity);

		} else if ("remove".equals(action)) {
			int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));

			cartDAO.removeItem(cartItemId);
		}

		response.sendRedirect("cart");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		User user = (session != null) ? (User) session.getAttribute("loggedInUser") : null;

		if (user == null) {
			response.sendRedirect("login");
			return;
		}

		request.setAttribute("cartItems", cartDAO.getCartItemsByUserId(user.getUserId()));

		request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
	}
}