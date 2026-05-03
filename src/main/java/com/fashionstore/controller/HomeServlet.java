package com.fashionstore.controller;

import com.fashionstore.dao.CategoryDAO;
import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.impl.CategoryDAOImpl;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Category;
import com.fashionstore.model.Product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final CategoryDAO categoryDAO = new CategoryDAOImpl();
	private final ProductDAO productDAO = new ProductDAOImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Fetch categories
		List<Category> categories = categoryDAO.getAllCategories();

		// Fetch latest products (we will implement this method next)
		List<Product> latestProducts = productDAO.getAllProducts();// check this

		// Send data to JSP
		request.setAttribute("categories", categories);
		request.setAttribute("latestProducts", latestProducts);

		// Forward to home page
		request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}
}