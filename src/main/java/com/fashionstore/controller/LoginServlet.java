package com.fashionstore.controller;

import com.fashionstore.dao.UserDAO;
import com.fashionstore.dao.impl.UserDAOImpl;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private final UserDAO userDAO = new UserDAOImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		User user = userDAO.loginUser(email, password);

		if (user != null) {
			HttpSession session = request.getSession();

			// Store full user
			session.setAttribute("loggedInUser", user);

			// 🔥 IMPORTANT FIX
			session.setAttribute("userId", user.getUserId());

			response.sendRedirect(request.getContextPath() + "/home");
		} else {
			request.setAttribute("error", "Invalid email or password");
			request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
		}
	}
}