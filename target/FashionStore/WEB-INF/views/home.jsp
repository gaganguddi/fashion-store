<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.Product" %>
<%@ page import="com.fashionstore.model.Category" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>FashionStore - Discover Your Style</title>
  <meta name="description" content="Shop the latest fashion trends at FashionStore. Premium collections for men, women and kids.">
  <link rel="icon" type="image/png" href="<%= request.getContextPath() %>/assets/images/favicon.png">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/home.css">
</head>
<body>

<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<!-- Marquee -->
<div class="marquee-bar">
  <span class="marquee-track">
    Free Shipping on orders above Rs.999 &nbsp;&bull;&nbsp; Up to 50% Off on New Arrivals &nbsp;&bull;&nbsp; Easy 30-day Returns &nbsp;&bull;&nbsp; Exclusive Member Deals &nbsp;&bull;&nbsp; New Collections Every Week &nbsp;&bull;&nbsp;
    Free Shipping on orders above Rs.999 &nbsp;&bull;&nbsp; Up to 50% Off on New Arrivals &nbsp;&bull;&nbsp; Easy 30-day Returns &nbsp;&bull;&nbsp; Exclusive Member Deals &nbsp;&bull;&nbsp; New Collections Every Week &nbsp;&bull;&nbsp;
  </span>
</div>

<!-- Hero -->
<div class="hero">
  <div class="hero-content">
    <div class="hero-badge">&#10024; New Season Arrivals are Here</div>
    <h1>Discover Your <span>Perfect Style</span></h1>
    <p>Explore hundreds of curated fashion collections. From casual wear to premium formals, we have everything for every occasion.</p>
    <div class="hero-actions">
      <a href="<%= request.getContextPath() %>/products" class="btn btn-accent">Shop Now &rarr;</a>
      <a href="<%= request.getContextPath() %>/products" class="btn btn-outline" style="background:rgba(255,255,255,0.1);color:#fff;border-color:rgba(255,255,255,0.4);">Browse Collections</a>
    </div>
    <div class="hero-stats">
      <div class="hero-stat"><strong>10K+</strong><span>Products</span></div>
      <div class="hero-stat"><strong>50K+</strong><span>Customers</span></div>
      <div class="hero-stat"><strong>100%</strong><span>Authentic</span></div>
    </div>
  </div>
</div>

<!-- Categories -->
<div class="categories-section">
  <div class="container">
    <div class="section-title">Shop by Category</div>
    <div class="cat-grid">
      <%
        String[] icons = {"&#128084;","&#128082;","&#128119;","&#128095;","&#128096;","&#128193;"};
        List<Category> categories = (List<Category>) request.getAttribute("categories");
        int ci = 0;
        if (categories != null) {
          for (Category c : categories) {
      %>
      <a href="<%= request.getContextPath() %>/products?categoryId=<%= c.getCategoryId() %>" class="cat-card">
        <span class="cat-icon"><%= ci < icons.length ? icons[ci] : "&#128248;" %></span>
        <h3><%= c.getCategoryName() %></h3>
        <p><%= c.getDescription() != null && c.getDescription().length() > 40 ? c.getDescription().substring(0,40)+"..." : (c.getDescription() != null ? c.getDescription() : "") %></p>
      </a>
      <% ci++; } } %>
    </div>
  </div>
</div>

<!-- Promo Banner -->
<div class="promo-banner">
  <div class="promo-content">
    <h2>Summer Sale is LIVE!</h2>
    <p>Grab up to 50% off on selected products. Limited time only!</p>
    <a href="<%= request.getContextPath() %>/products" class="btn btn-primary" style="background:#fff;color:var(--accent);">Shop the Sale</a>
  </div>
</div>

<!-- Latest Products -->
<div class="products-section">
  <div class="section-title">Latest Products</div>
  <div class="home-product-grid">
    <%
      List<Product> products = (List<Product>) request.getAttribute("latestProducts");
      if (products != null) {
        for (Product p : products) {
          double discPct = p.getDiscountPercent();
    %>
    <div class="home-product-card">
      <% if (discPct > 0) { %><div class="discount-badge"><%= (int)discPct %>% OFF</div><% } %>
      <button class="wishlist-btn" onclick="toggleWish(this, <%= p.getProductId() %>)" title="Wishlist">&#9825;</button>
      <div class="card-img-wrap">
        <img src="<%= request.getContextPath() + "/" + p.getImageUrl() %>" alt="<%= p.getProductName() %>">
        <div class="card-overlay">
          <a href="<%= request.getContextPath() %>/product-details?productId=<%= p.getProductId() %>" class="btn btn-primary">Quick View</a>
        </div>
      </div>
      <div class="card-body">
        <div class="card-brand"><%= p.getBrand() != null ? p.getBrand() : "FashionStore" %></div>
        <div class="card-name"><%= p.getProductName() %></div>
        <div class="card-price">
          <span class="price">&#8377; <%= p.getPrice() %></span>
          <% if (discPct > 0) { double orig = p.getPrice() / (1 - discPct/100); %>
          <span class="old-price">&#8377; <%= String.format("%.0f", orig) %></span>
          <% } %>
        </div>
        <div class="card-stars">&#9733;&#9733;&#9733;&#9733;&#9734; <span style="color:#888;font-size:11px;">(42)</span></div>
      </div>
    </div>
    <% } } %>
  </div>
</div>

<!-- Features -->
<div class="features-section">
  <div class="feature-item"><div class="f-icon">&#128230;</div><h3>Free Shipping</h3><p>On all orders above Rs. 999</p></div>
  <div class="feature-item"><div class="f-icon">&#128260;</div><h3>Easy Returns</h3><p>30-day hassle-free returns</p></div>
  <div class="feature-item"><div class="f-icon">&#128274;</div><h3>Secure Payment</h3><p>100% safe transactions</p></div>
  <div class="feature-item"><div class="f-icon">&#128222;</div><h3>24/7 Support</h3><p>Always here to help</p></div>
</div>

<jsp:include page="/WEB-INF/views/partials/footer.jsp" />

<script>
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
</script>
</body>
</html>
