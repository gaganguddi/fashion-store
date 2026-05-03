<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.fashionstore.model.User" %>
<%
    User navUser = (User) session.getAttribute("loggedInUser");
    String initials = "?";
    if (navUser != null && navUser.getFullName() != null && !navUser.getFullName().isEmpty()) {
        String[] parts = navUser.getFullName().trim().split("\\s+");
        initials = parts.length >= 2
            ? ("" + parts[0].charAt(0) + parts[parts.length-1].charAt(0)).toUpperCase()
            : ("" + parts[0].charAt(0)).toUpperCase();
    }
%>
<nav class="navbar">

    <div class="nav-left">
        <span class="logo-icon">👗</span>
        <span class="logo">FashionStore</span>
    </div>

    <div class="nav-center">
        <form action="<%= request.getContextPath() %>/products" method="get" class="search-wrap">
            <span class="search-icon">🔍</span>
            <input type="text" name="search" placeholder="Search for products, brands..." value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>" />
        </form>
    </div>

    <div class="nav-right">
        <a href="<%= request.getContextPath() %>/home"><span>🏠</span><span>Home</span></a>
        <a href="<%= request.getContextPath() %>/products"><span>🛍️</span><span>Shop</span></a>
        <a href="<%= request.getContextPath() %>/wishlist"><span>&#9825;</span><span>Wishlist</span></a>
        <a href="<%= request.getContextPath() %>/cart"><span>🛒</span><span>Cart</span></a>

        <% if (navUser != null) { %>

        <div class="nav-dropdown">
            <div class="nav-avatar"><%= initials %></div>
            <div class="nav-dropdown-menu">
                <div style="padding:12px 14px 8px;border-bottom:1px solid #eee;margin-bottom:4px;">
                    <div style="font-weight:700;font-size:14px;color:#1a1a2e;"><%= navUser.getFullName() %></div>
                    <div style="font-size:12px;color:#888;margin-top:2px;"><%= navUser.getEmail() %></div>
                </div>
                <a href="<%= request.getContextPath() %>/profile">👤 My Profile</a>
                <a href="<%= request.getContextPath() %>/wishlist">&#9825; My Wishlist</a>
                <a href="<%= request.getContextPath() %>/order-history">📦 My Orders</a>
                <div class="divider"></div>
                <a href="<%= request.getContextPath() %>/logout" class="logout-link">🚪 Logout</a>
            </div>
        </div>

        <% } else { %>
        <a href="<%= request.getContextPath() %>/login" style="background:rgba(255,255,255,0.15);border:1px solid rgba(255,255,255,0.3);">
            <span>🔑</span><span>Login</span>
        </a>
        <% } %>
    </div>

</nav>