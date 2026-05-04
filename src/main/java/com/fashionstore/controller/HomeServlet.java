package com.fashionstore.controller;

import com.fashionstore.dao.CategoryDAO;
import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.impl.CategoryDAOImpl;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Category;
import com.fashionstore.model.Product;
import com.fashionstore.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final CategoryDAO categoryDAO = new CategoryDAOImpl();
    private final ProductDAO productDAO = new ProductDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("===== PROCESSING HOME REQUEST =====");

        try (Connection testConn = DBConnection.getConnection()) {
            if (testConn == null) {
                System.out.println("❌ CRITICAL: Could not establish database connection in HomeServlet");
                request.setAttribute("errorMessage", "Database connection failed. Please check environment variables.");
                // You can optionally forward to an error page here
            } else {
                System.out.println("✅ DB Connection test passed");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Connection test error: " + e.getMessage());
        }

        // Fetch data
        List<Category> categories = categoryDAO.getAllCategories();
        List<Product> latestProducts = productDAO.getAllProducts();

        request.setAttribute("categories", categories);
        request.setAttribute("latestProducts", latestProducts);

        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }
}