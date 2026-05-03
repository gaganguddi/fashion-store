package com.fashionstore.controller;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.ProductVariantDAO;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.dao.impl.ProductVariantDAOImpl;
import com.fashionstore.model.Product;
import com.fashionstore.model.ProductVariant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/product-details")
public class ProductDetailsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private final ProductDAO productDAO = new ProductDAOImpl();
	private final ProductVariantDAO variantDAO = new ProductVariantDAOImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String productIdParam = request.getParameter("productId");

		if (productIdParam == null || productIdParam.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/products");
			return;
		}

		int productId;
		try {
			productId = Integer.parseInt(productIdParam);
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/products");
			return;
		}

		// ✅ Get main product
		Product product = productDAO.getProductById(productId);

		if (product == null) {
			response.sendRedirect(request.getContextPath() + "/products");
			return;
		}

		// ✅ Get variants
		List<ProductVariant> variants = variantDAO.getVariantsByProductId(productId);

		// 🔥 NEW: Get related products
		List<Product> relatedProducts = productDAO.getRelatedProducts(product.getCategoryId(), productId);

		// ✅ Set attributes
		request.setAttribute("product", product);
		request.setAttribute("variants", variants);
		request.setAttribute("relatedProducts", relatedProducts);

		// ✅ Forward
		request.getRequestDispatcher("/WEB-INF/views/product-details.jsp").forward(request, response);
	}
}