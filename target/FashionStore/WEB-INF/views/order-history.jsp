<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.Order" %>
<%@ page import="com.fashionstore.model.OrderItem" %>
<%@ page import="com.fashionstore.model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>My Orders - FashionStore</title>
  <link rel="icon" type="image/png" href="<%= request.getContextPath() %>/assets/images/favicon.png">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
  <style>
    .orders-layout { max-width: 1100px; margin: 40px auto; padding: 0 20px; display: grid; grid-template-columns: 260px 1fr; gap: 30px; }
    .orders-sidebar {
      background: var(--surface);
      border-radius: var(--radius);
      border: 1px solid var(--border);
      padding: 24px;
      height: fit-content;
      position: sticky;
      top: 90px;
    }
    .orders-sidebar h3 { font-size: 14px; font-weight: 800; margin-bottom: 16px; color: var(--text-muted); text-transform: uppercase; letter-spacing: 0.5px; }
    .sidebar-menu a {
      display: flex; align-items: center; gap: 10px; padding: 12px; border-radius: var(--radius-sm);
      font-size: 14px; font-weight: 500; color: var(--text-muted); transition: var(--transition); margin-bottom: 4px;
    }
    .sidebar-menu a:hover, .sidebar-menu a.active { background: rgba(106,92,255,0.08); color: var(--primary); }
    
    .order-card {
      background: var(--surface);
      border-radius: var(--radius);
      border: 1px solid var(--border);
      margin-bottom: 24px;
      overflow: hidden;
      box-shadow: var(--shadow-sm);
    }
    .order-header {
      display: flex; justify-content: space-between; align-items: center;
      padding: 16px 24px; background: var(--surface2); border-bottom: 1px solid var(--border);
    }
    .order-id { font-weight: 800; font-size: 15px; }
    .order-body { padding: 0 24px; }
    .order-item-row {
      display: flex; align-items: center; gap: 16px; padding: 16px 0; border-bottom: 1px solid var(--border);
    }
    .order-item-row:last-child { border-bottom: none; }
    .item-img { width: 60px; height: 60px; border-radius: 8px; object-fit: cover; background: #f0f0f8; }
    .item-info { flex: 1; }
    .item-name { font-weight: 700; font-size: 14px; display: block; margin-bottom: 4px; }
    .item-meta { font-size: 12px; color: var(--text-muted); }
    .item-price { font-weight: 700; color: var(--primary); font-size: 14px; }
    
    .order-footer {
      padding: 16px 24px; display: flex; justify-content: space-between; align-items: center;
      background: var(--surface2); border-top: 1px solid var(--border);
    }
    .order-total { font-weight: 800; font-size: 16px; color: var(--primary); }
    .empty-orders { text-align: center; padding: 80px 20px; color: var(--text-muted); grid-column: 1/-1; }
    
    @media(max-width:800px) {
      .orders-layout { grid-template-columns: 1fr; }
      .orders-sidebar { display: none; }
    }
  </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<div class="page-header">
  <h1>My Orders</h1>
  <p>Track and manage all your purchases in one place</p>
</div>

<div class="orders-layout">
  <div class="orders-sidebar">
    <h3>Account</h3>
    <div class="sidebar-menu">
      <a href="<%= request.getContextPath() %>/profile">&#128100; My Profile</a>
      <a href="<%= request.getContextPath() %>/order-history" class="active">&#128230; My Orders</a>
      <a href="<%= request.getContextPath() %>/wishlist">&#9825; My Wishlist</a>
      <a href="<%= request.getContextPath() %>/cart">&#128722; My Cart</a>
    </div>
  </div>

  <div class="orders-main">
    <% if (request.getParameter("success") != null) { %>
      <div style="background:var(--success);color:white;padding:12px 20px;border-radius:var(--radius-sm);margin-bottom:20px;font-size:14px;">
        <%= request.getParameter("success") %>
      </div>
    <% } %>
    <% if (request.getParameter("error") != null) { %>
      <div style="background:var(--danger);color:white;padding:12px 20px;border-radius:var(--radius-sm);margin-bottom:20px;font-size:14px;">
        <%= request.getParameter("error") %>
      </div>
    <% } %>

    <%
      List<Order> orders = (List<Order>) request.getAttribute("orders");
      if (orders != null && !orders.isEmpty()) {
        for (Order o : orders) {
          String status = o.getOrderStatus() != null ? o.getOrderStatus().toUpperCase() : "PENDING";
          boolean canCancel = status.equals("PENDING") || status.equals("PLACED") || status.equals("PROCESSING");
    %>
    <div class="order-card">
      <div class="order-header">
        <div>
          <div class="order-id">Order #<%= o.getOrderId() %></div>
          <div style="font-size:11px;color:var(--text-muted);margin-top:2px;"><%= o.getOrderDate() %></div>
        </div>
        <span class="badge <%= status.contains("DELIVER") ? "badge-success" : (status.equals("CANCELLED") ? "badge-danger" : "badge-primary") %>">
          <%= status %>
        </span>
      </div>
      <div class="order-body">
        <% if (o.getItems() != null) { for (OrderItem item : o.getItems()) { %>
        <div class="order-item-row">
          <img src="<%= request.getContextPath() + "/" + (item.getImageUrl() != null ? item.getImageUrl() : "assets/images/placeholder.jpg") %>" alt="<%= item.getProductName() %>" class="item-img">
          <div class="item-info">
            <span class="item-name"><%= item.getProductName() %></span>
            <span class="item-meta">Quantity: <%= item.getQuantity() %></span>
          </div>
          <div class="item-price">&#8377; <%= item.getPrice() %></div>
        </div>
        <% } } %>
      </div>
      <div class="order-footer">
        <div style="font-size:13px;color:var(--text-muted);">Payment: <%= o.getPaymentMethod() != null ? o.getPaymentMethod() : "N/A" %></div>
        <div class="order-total">Total: &#8377; <%= o.getTotalAmount() %></div>
      </div>
      <% if (canCancel) { %>
      <div style="padding:12px 24px; background:rgba(0,0,0,0.01); display:flex; justify-content:flex-end; border-top:1px dashed var(--border);">
        <form action="<%= request.getContextPath() %>/cancel-order" method="post" onsubmit="return confirm('Are you sure you want to cancel this order?');">
          <input type="hidden" name="orderId" value="<%= o.getOrderId() %>">
          <button type="submit" class="btn btn-danger" style="padding:7px 16px; font-size:12px;">Cancel Order</button>
        </form>
      </div>
      <% } %>
    </div>
    <% } } else { %>
    <div class="empty-orders">
      <div style="font-size:64px;margin-bottom:20px;">&#128230;</div>
      <h2>No orders yet</h2>
      <p style="margin:10px 0 24px;">Looks like you haven't placed any orders. Start shopping!</p>
      <a href="<%= request.getContextPath() %>/products" class="btn btn-primary">Browse Products</a>
    </div>
    <% } %>
  </div>
</div>

<jsp:include page="/WEB-INF/views/partials/footer.jsp" />
</body>
</html>
