<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Login - FashionStore</title>
  <link rel="icon" type="image/png" href="<%= request.getContextPath() %>/assets/images/favicon.png">
  <meta name="description" content="Login to your FashionStore account.">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/auth.css">
</head>
<body>
<div class="auth-page">
  <div class="auth-box">
    <div class="auth-logo">
      <span>&#128084;</span>
      <h2>Welcome Back</h2>
      <p>Sign in to continue to FashionStore</p>
    </div>

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-error">&#9888; <%= request.getAttribute("error") %></div>
    <% } %>

    <form method="post" action="<%= request.getContextPath() %>/login">
      <div class="form-group">
        <label>Email Address</label>
        <input class="form-control" type="email" name="email" placeholder="you@example.com" required>
      </div>
      <div class="form-group">
        <label>Password</label>
        <input class="form-control" type="password" name="password" placeholder="Enter your password" required>
      </div>
      <button type="submit" class="auth-submit">Sign In &rarr;</button>
    </form>

    <div class="auth-divider">or</div>
    <div class="auth-footer">
      Don't have an account? <a href="<%= request.getContextPath() %>/register">Create one</a>
    </div>
  </div>
</div>
</body>
</html>
