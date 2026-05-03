<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.CartItem" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Shopping Cart - FashionStore</title>
  <link rel="icon" type="image/png" href="<%= request.getContextPath() %>/assets/images/favicon.png">
  <meta name="description" content="View and manage your FashionStore shopping cart.">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
  <style>
    .cart-page { max-width: 1050px; margin: 0 auto; padding: 40px 20px; display: grid; grid-template-columns: 1fr 340px; gap: 28px; }
    .cart-items-col {}
    .cart-summary-col {}

    /* Cart item */
    .cart-item-card {
      background: var(--surface);
      border-radius: var(--radius);
      border: 1px solid var(--border);
      padding: 18px 20px;
      display: flex;
      align-items: center;
      gap: 18px;
      margin-bottom: 14px;
      box-shadow: var(--shadow-sm);
      transition: var(--transition);
    }
    .cart-item-card:hover { box-shadow: var(--shadow-md); }
    .item-img {
      width: 90px; height: 90px; object-fit: cover;
      border-radius: var(--radius-sm); background: #f0f0f8; flex-shrink: 0;
    }
    .item-details { flex: 1; min-width: 0; }
    .item-name { font-size: 15px; font-weight: 700; margin-bottom: 4px; }
    .item-meta { font-size: 12px; color: var(--text-muted); margin-bottom: 10px; }
    .item-price { font-size: 16px; font-weight: 800; color: var(--primary); }
    .item-actions { display: flex; align-items: center; gap: 10px; }
    .qty-form { display: flex; align-items: center; gap: 6px; }
    .qty-form input {
      width: 55px; padding: 6px 8px; border: 2px solid var(--border);
      border-radius: var(--radius-sm); text-align: center;
      font-size: 14px; font-weight: 600;
    }
    .qty-form button {
      padding: 7px 14px;
      background: var(--primary); color: white;
      border: none; border-radius: var(--radius-sm);
      font-size: 12px; font-weight: 600; cursor: pointer;
      transition: var(--transition);
    }
    .qty-form button:hover { background: var(--primary-dark); }
    .remove-btn {
      padding: 7px 12px;
      background: rgba(239,68,68,0.08); color: var(--danger);
      border: 1px solid rgba(239,68,68,0.2); border-radius: var(--radius-sm);
      font-size: 12px; font-weight: 600; cursor: pointer;
      transition: var(--transition);
    }
    .remove-btn:hover { background: var(--danger); color: white; }
    .subtotal { font-size: 14px; font-weight: 700; color: var(--text-muted); white-space: nowrap; }

    /* Summary */
    .summary-card {
      background: var(--surface);
      border-radius: var(--radius);
      border: 1px solid var(--border);
      box-shadow: var(--shadow-sm);
      padding: 24px;
      position: sticky;
      top: 90px;
      height: fit-content;
    }
    .summary-card h3 { font-size: 17px; font-weight: 800; margin-bottom: 20px; }
    .summary-row {
      display: flex; justify-content: space-between;
      font-size: 14px; margin-bottom: 12px; color: var(--text-muted);
    }
    .summary-row.total {
      font-size: 17px; font-weight: 900; color: var(--text);
      border-top: 2px solid var(--border); padding-top: 14px; margin-top: 8px;
    }
    .summary-row.total span:last-child { color: var(--primary); }
    .checkout-btn-full {
      display: block; width: 100%; text-align: center;
      padding: 15px; margin-top: 20px;
      background: linear-gradient(135deg, var(--primary), var(--primary-light));
      color: white; border-radius: var(--radius-sm);
      font-weight: 700; font-size: 15px; border: none; cursor: pointer;
      box-shadow: 0 4px 15px rgba(106,92,255,0.4); transition: var(--transition);
      text-decoration: none;
    }
    .checkout-btn-full:hover { transform: translateY(-2px); box-shadow: 0 8px 25px rgba(106,92,255,0.5); }
    .continue-shop { display: block; text-align: center; margin-top: 12px; font-size: 13px; color: var(--primary); }

    /* Empty cart */
    .empty-cart { background: var(--surface); border-radius: var(--radius); padding: 80px 40px; text-align: center; border: 1px solid var(--border); box-shadow: var(--shadow-sm); }
    .empty-cart .cart-icon { font-size: 72px; margin-bottom: 20px; }
    .empty-cart h2 { font-size: 22px; font-weight: 800; margin-bottom: 8px; }
    .empty-cart p { color: var(--text-muted); margin-bottom: 24px; }

    @media(max-width:800px) {
      .cart-page { grid-template-columns: 1fr; }
      .summary-card { position: static; }
      .cart-item-card { flex-wrap: wrap; }
    }
  </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<div class="page-header">
  <h1>Shopping Cart</h1>
  <p>Review your selected items before checkout</p>
</div>

<%
  List<CartItem> items = (List<CartItem>) request.getAttribute("cartItems");
  double total = 0;
  if (items != null && !items.isEmpty()) {
    for (CartItem item : items) total += item.getSubtotal();
%>

<div class="cart-page">

  <div class="cart-items-col">
    <div style="font-size:14px;color:var(--text-muted);margin-bottom:16px;font-weight:600;"><%= items.size() %> item(s) in your cart</div>

    <% for (CartItem item : items) { %>
    <div class="cart-item-card">
      <img class="item-img" src="<%= request.getContextPath() + "/" + (item.getImageUrl() != null ? item.getImageUrl() : "assets/images/placeholder.jpg") %>"
           onerror="this.style.background='#f0f0f8';this.removeAttribute('src')" alt="<%= item.getProductName() %>">
      <div class="item-details">
        <div class="item-name"><%= item.getProductName() %></div>
        <div class="item-meta">Size: <strong><%= item.getSize() %></strong> &nbsp; Unit Price: &#8377; <%= String.format("%.2f", item.getUnitPrice()) %></div>
        <div class="item-price">&#8377; <%= String.format("%.2f", item.getSubtotal()) %></div>
        <div class="item-actions" style="margin-top:10px;">
          <form method="post" action="<%= request.getContextPath() %>/cart" class="qty-form">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="cartItemId" value="<%= item.getCartItemId() %>">
            <input type="number" name="quantity" value="<%= item.getQuantity() %>" min="1" max="10">
            <button type="submit">Update</button>
          </form>
          <form method="post" action="<%= request.getContextPath() %>/cart">
            <input type="hidden" name="action" value="remove">
            <input type="hidden" name="cartItemId" value="<%= item.getCartItemId() %>">
            <button class="remove-btn" type="submit">&#128465; Remove</button>
          </form>
        </div>
      </div>
    </div>
    <% } %>
  </div>

  <div class="cart-summary-col">
    <div class="summary-card">
      <h3>Order Summary</h3>
      <div class="summary-row"><span>Subtotal</span><span>&#8377; <%= String.format("%.2f", total) %></span></div>
      <div class="summary-row"><span>Shipping</span><span style="color:var(--success);">FREE</span></div>
      <div class="summary-row"><span>Discount</span><span>&#8377; 0.00</span></div>
      <div class="summary-row total"><span>Total</span><span>&#8377; <%= String.format("%.2f", total) %></span></div>
      <a href="<%= request.getContextPath() %>/checkout" class="checkout-btn-full">Proceed to Checkout &rarr;</a>
      <a href="<%= request.getContextPath() %>/products" class="continue-shop">&#8592; Continue Shopping</a>
    </div>
  </div>

</div>

<% } else { %>
<div style="max-width:600px;margin:40px auto;padding:0 20px;">
  <div class="empty-cart">
    <div class="cart-icon">&#128722;</div>
    <h2>Your cart is empty</h2>
    <p>Looks like you haven't added anything yet. Let's fix that!</p>
    <a href="<%= request.getContextPath() %>/products" class="btn btn-primary">Browse Products</a>
  </div>
</div>
<% } %>

<jsp:include page="/WEB-INF/views/partials/footer.jsp" />
</body>
</html>
