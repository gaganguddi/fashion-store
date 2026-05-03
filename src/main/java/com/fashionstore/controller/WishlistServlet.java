package com.fashionstore.controller;

import com.fashionstore.dao.WishlistDAO;
import com.fashionstore.dao.impl.WishlistDAOImpl;
import com.fashionstore.model.User;
import com.fashionstore.model.WishlistItem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/wishlist")
public class WishlistServlet extends HttpServlet {
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

        List<WishlistItem> wishlistItems = wishlistDAO.getWishlistByUserId(user.getUserId());
        request.setAttribute("wishlistItems", wishlistItems);
        request.getRequestDispatcher("/WEB-INF/views/wishlist.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("loggedInUser") : null;

        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("unauthorized");
            return;
        }

        String action = request.getParameter("action");
        int productId = Integer.parseInt(request.getParameter("productId"));

        boolean success = false;
        if ("add".equals(action)) {
            success = wishlistDAO.addToWishlist(user.getUserId(), productId);
        } else if ("remove".equals(action)) {
            success = wishlistDAO.removeFromWishlist(user.getUserId(), productId);
        }

        response.setContentType("text/plain");
        response.getWriter().write(success ? "success" : "error");
    }
}
