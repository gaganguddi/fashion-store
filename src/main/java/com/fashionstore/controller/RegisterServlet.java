package com.fashionstore.controller;

import com.fashionstore.dao.UserDAO;
import com.fashionstore.dao.impl.UserDAOImpl;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

	private final UserDAO userDAO = new UserDAOImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User user = new User();
		user.setFullName(request.getParameter("fullName"));
		user.setEmail(request.getParameter("email"));
		user.setPhone(request.getParameter("phone"));
		user.setPassword(request.getParameter("password"));
		user.setAddressLine1(request.getParameter("addressLine1"));
		user.setAddressLine2(request.getParameter("addressLine2"));
		user.setCity(request.getParameter("city"));
		user.setState(request.getParameter("state"));
		user.setPincode(request.getParameter("pincode"));
		user.setCountry("India");

		boolean success = userDAO.registerUser(user);

		if (success) {
			response.sendRedirect(request.getContextPath() + "/login");
		} else {
			request.setAttribute("error", "Registration failed");
			request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
		}
	}
}