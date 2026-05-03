package com.fashionstore.controller;

import com.fashionstore.dao.OrderDAO;
import com.fashionstore.dao.impl.OrderDAOImpl;
import com.fashionstore.model.Order;
import com.fashionstore.model.OrderItem;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/order-history")
public class OrderHistoryServlet extends HttpServlet {

	private final OrderDAO orderDAO = new OrderDAOImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		User user = (User) session.getAttribute("loggedInUser");

		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}

		int userId = user.getUserId();

		// ✅ Get Orders
		List<Order> orders = orderDAO.getOrdersByUserId(userId);

		// ✅ Attach Items
		for (Order order : orders) {
			List<OrderItem> items = orderDAO.getOrderItemsByOrderId(order.getOrderId());

			order.setItems(items);
		}

		request.setAttribute("orders", orders);

		request.getRequestDispatcher("/WEB-INF/views/order-history.jsp").forward(request, response);
	}
}