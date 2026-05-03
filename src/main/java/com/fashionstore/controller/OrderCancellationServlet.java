package com.fashionstore.controller;

import com.fashionstore.dao.OrderDAO;
import com.fashionstore.dao.impl.OrderDAOImpl;
import com.fashionstore.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/cancel-order")
public class OrderCancellationServlet extends HttpServlet {
    private final OrderDAO orderDAO = new OrderDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("loggedInUser") : null;

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            
            // Basic security check: ensure order belongs to user
            com.fashionstore.model.Order order = orderDAO.getOrderById(orderId);
            String status = (order != null && order.getOrderStatus() != null) ? order.getOrderStatus().toUpperCase() : "";
            boolean canCancel = status.equals("PENDING") || status.equals("PLACED") || status.equals("PROCESSING");
            
            if (order != null && order.getUserId() == user.getUserId() && canCancel) {
                boolean success = orderDAO.updateOrderStatus(orderId, "Cancelled");
                if (success) {
                    response.sendRedirect(request.getContextPath() + "/order-history?success=Order cancelled successfully");
                } else {
                    response.sendRedirect(request.getContextPath() + "/order-history?error=Failed to cancel order");
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/order-history?error=Order cannot be cancelled");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/order-history?error=Invalid order ID");
        }
    }
}
