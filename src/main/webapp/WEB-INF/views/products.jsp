<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.Product" %>
<%@ page import="com.fashionstore.model.Category" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Products - FashionStore</title>
  <meta name="description" content="Browse our complete collection of fashion products at FashionStore.">
  <link rel="icon" type="image/png" href="<%= request.getContextPath() %>/assets/images/favicon.png">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/products.css">
</head>
<body>

<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<div class="products-header">
  <h1>All Products</h1>
  <p>Explore our trendy fashion collections &mdash; <strong style="color:rgba(255,255,255,0.9);">New arrivals every week!</strong></p>
</div>

<div class="products-layout">

  <!-- Sidebar -->
  <div class="sidebar">
    <h3>Categories</h3>
    <a href="<%= request.getContextPath() %>/products" class="filter-link">&#127758; All Products</a>
    <%
      String[] catIcons = {"&#128084;","&#128082;","&#128119;","&#128095;","&#128096;"};
      List<Category> categories = (List<Category>) request.getAttribute("categories");
      int ci2 = 0;
      if (categories != null) {
        for (Category c : categories) {
    %>
    <a href="<%= request.getContextPath() %>/products?categoryId=<%= c.getCategoryId() %>" class="filter-link">
      <%= ci2 < catIcons.length ? catIcons[ci2] : "&#9632;" %>
      <%= c.getCategoryName() %>
    </a>
    <% ci2++; } } %>

    <div style="margin-top:24px;padding-top:16px;border-top:1px solid var(--border);">
      <h3 style="margin-bottom:12px;">Price Range</h3>
      <div style="font-size:13px;color:var(--text-muted);">
        <label style="display:flex;align-items:center;gap:8px;margin-bottom:8px;cursor:pointer;">
          <input type="radio" name="price" value="all" checked> All Prices
        </label>
        <label style="display:flex;align-items:center;gap:8px;margin-bottom:8px;cursor:pointer;">
          <input type="radio" name="price" value="low"> Under &#8377;500
        </label>
        <label style="display:flex;align-items:center;gap:8px;margin-bottom:8px;cursor:pointer;">
          <input type="radio" name="price" value="mid"> &#8377;500 - &#8377;2000
        </label>
        <label style="display:flex;align-items:center;gap:8px;cursor:pointer;">
          <input type="radio" name="price" value="high"> Above &#8377;2000
        </label>
      </div>
    </div>
  </div>

  <!-- Product Grid -->
  <div class="product-section">
    <div class="sort-bar">
      <span id="product-count">Showing all products</span>
      <select onchange="sortProducts(this.value)">
        <option value="">Sort by: Featured</option>
        <option value="low">Price: Low to High</option>
        <option value="high">Price: High to Low</option>
        <option value="name">Name: A to Z</option>
      </select>
    </div>

    <div class="product-grid" id="productGrid">
      <%
        List<Product> products = (List<Product>) request.getAttribute("products");
        if (products != null && !products.isEmpty()) {
          for (Product p : products) {
      %>
      <div class="product-card" data-price="<%= p.getPrice() %>" data-name="<%= p.getProductName() %>">
        <div style="position:relative;overflow:hidden;">
          <img src="<%= request.getContextPath() + "/" + p.getImageUrl() %>" alt="<%= p.getProductName() %>">
          <button class="wishlist-btn-overlay" onclick="toggleWish(this, <%= p.getProductId() %>)" title="Add to Wishlist">&#9825;</button>
          <% if (p.getDiscountPercent() > 0) { %>
          <span style="position:absolute;top:10px;left:10px;background:var(--accent);color:white;font-size:10px;font-weight:700;padding:3px 9px;border-radius:20px;"><%= (int)p.getDiscountPercent() %>% OFF</span>
          <% } %>
        </div>
        <div class="product-card-body">
          <div class="brand"><%= p.getBrand() != null ? p.getBrand() : "FashionStore" %></div>
          <h3><%= p.getProductName() %></h3>
          <p class="price">&#8377; <%= p.getPrice() %></p>
          <div style="font-size:12px;color:var(--gold);margin-bottom:10px;">&#9733;&#9733;&#9733;&#9733;&#9734;</div>
          <a href="<%= request.getContextPath() %>/product-details?productId=<%= p.getProductId() %>" class="view-btn">View Details</a>
        </div>
      </div>
      <%   }
        } else { %>
      <div class="no-products">
        <div style="font-size:50px;margin-bottom:16px;">&#128247;</div>
        <h3>No products found</h3>
        <p style="color:var(--text-muted);margin-top:8px;">Try a different category or search term.</p>
      </div>
      <% } %>
    </div>
  </div>
</div>

<jsp:include page="/WEB-INF/views/partials/footer.jsp" />

<script>
function sortProducts(val) {
  var grid = document.getElementById('productGrid');
  var cards = Array.from(grid.querySelectorAll('.product-card'));
  cards.sort(function(a, b) {
    if (val === 'low') return parseFloat(a.dataset.price) - parseFloat(b.dataset.price);
    if (val === 'high') return parseFloat(b.dataset.price) - parseFloat(a.dataset.price);
    if (val === 'name') return a.dataset.name.localeCompare(b.dataset.name);
    return 0;
  });
  cards.forEach(function(c) { grid.appendChild(c); });
}

function toggleWish(btn, productId) {
  const isAdded = btn.textContent.trim() === '\u2665';
  const action = isAdded ? 'remove' : 'add';
  
  fetch('<%= request.getContextPath() %>/wishlist?action=' + action + '&productId=' + productId, {
    method: 'POST'
  })
  .then(response => response.text())
  .then(data => {
    if (data === 'success') {
      btn.textContent = isAdded ? '\u2661' : '\u2665';
      btn.style.color = isAdded ? '' : '#ff6b9d';
    } else if (data === 'unauthorized') {
      window.location.href = '<%= request.getContextPath() %>/login';
    }
  });
}
document.getElementById('product-count').textContent = 'Showing ' + document.querySelectorAll('.product-card').length + ' products';
</script>
</body>
</html>
