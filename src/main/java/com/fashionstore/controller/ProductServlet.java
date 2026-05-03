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

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final ProductDAO productDAO = new ProductDAOImpl();
	private final CategoryDAO categoryDAO = new CategoryDAOImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String search = request.getParameter("search");
		String categoryIdParam = request.getParameter("categoryId");

		List<Product> products;

		// 🔍 Search filter
		if (search != null && !search.trim().isEmpty()) {
			products = productDAO.searchProducts(search);
		}
		// 📂 Category filter
		else if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
			int categoryId = Integer.parseInt(categoryIdParam);
			products = productDAO.getProductsByCategory(categoryId);
		}
		// 📦 Default: all products
		else {
			products = productDAO.getAllProducts();
		}

		// Load categories for filter sidebar
		List<Category> categories = categoryDAO.getAllCategories();

		request.setAttribute("products", products);
		request.setAttribute("categories", categories);

		request.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(request, response);
	}
}