package com.fashionstore.controller;

import com.fashionstore.dao.OrderDAO;
import com.fashionstore.dao.WishlistDAO;
import com.fashionstore.dao.impl.OrderDAOImpl;
import com.fashionstore.dao.impl.WishlistDAOImpl;
import com.fashionstore.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private final OrderDAO orderDAO = new OrderDAOImpl();
    private final WishlistDAO wishlistDAO = new WishlistDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("loggedInUser") : null;
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        int orderCount = orderDAO.getOrderCountByUserId(user.getUserId());
        int wishlistCount = wishlistDAO.getWishlistCountByUserId(user.getUserId());
        
        request.setAttribute("orderCount", orderCount);
        request.setAttribute("wishlistCount", wishlistCount);
        
        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }
}
