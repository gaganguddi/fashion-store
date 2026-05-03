<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Register - FashionStore</title>
  <link rel="icon" type="image/png" href="<%= request.getContextPath() %>/assets/images/favicon.png">
  <meta name="description" content="Create your FashionStore account.">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/auth.css">
  <style>
    .auth-box { max-width: 560px; }
    .form-row { display: grid; grid-template-columns: 1fr 1fr; gap: 14px; }
    @media(max-width:500px){ .form-row { grid-template-columns:1fr; } }
  </style>
</head>
<body>
<div class="auth-page">
  <div class="auth-box">
    <div class="auth-logo">
      <span>&#9989;</span>
      <h2>Create Account</h2>
      <p>Join thousands of fashion lovers</p>
    </div>

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-error">&#9888; <%= request.getAttribute("error") %></div>
    <% } %>

    <form method="post" action="<%= request.getContextPath() %>/register">
      <div class="form-row">
        <div class="form-group">
          <label>Full Name</label>
          <input class="form-control" type="text" name="fullName" placeholder="Rahul Sharma" required>
        </div>
        <div class="form-group">
          <label>Email</label>
          <input class="form-control" type="email" name="email" placeholder="you@example.com" required>
        </div>
      </div>
      <div class="form-row">
        <div class="form-group">
          <label>Phone</label>
          <input class="form-control" type="text" name="phone" placeholder="+91 9999999999" required>
        </div>
        <div class="form-group">
          <label>Password</label>
          <input class="form-control" type="password" name="password" placeholder="Min 6 characters" required>
        </div>
      </div>
      <div class="form-group">
        <label>Address Line 1</label>
        <input class="form-control" type="text" name="addressLine1" placeholder="House/Flat No, Street" required>
      </div>
      <div class="form-group">
        <label>Address Line 2 (optional)</label>
        <input class="form-control" type="text" name="addressLine2" placeholder="Landmark, Area">
      </div>
      <div class="form-row">
        <div class="form-group">
          <label>City</label>
          <input class="form-control" type="text" name="city" placeholder="Mumbai" required>
        </div>
        <div class="form-group">
          <label>State</label>
          <input class="form-control" type="text" name="state" placeholder="Maharashtra" required>
        </div>
      </div>
      <div class="form-row">
        <div class="form-group">
          <label>Pincode</label>
          <input class="form-control" type="text" name="pincode" placeholder="400001" required>
        </div>
        <div class="form-group">
          <label>Country</label>
          <input class="form-control" type="text" name="country" placeholder="India" value="India">
        </div>
      </div>
      <button type="submit" class="auth-submit">Create Account &rarr;</button>
    </form>
    <div class="auth-footer" style="margin-top:16px;">
      Already have an account? <a href="<%= request.getContextPath() %>/login">Sign In</a>
    </div>
  </div>
</div>
</body>
</html>
