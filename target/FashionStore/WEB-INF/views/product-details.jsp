<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.Product" %>
<%@ page import="com.fashionstore.model.ProductVariant" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Product Details - FashionStore</title>
  <link rel="icon" type="image/png" href="<%= request.getContextPath() %>/assets/images/favicon.png">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css?v=2">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/product-details.css">
  <style>
    /* Direct Review Styling */
    .reviews-section { margin-top: 60px; background: white; padding: 40px; border-radius: 24px; border: 1px solid #edf2f7; box-shadow: 0 10px 30px rgba(0,0,0,0.03); }
    .reviewer-avatar { width: 48px; height: 48px; border-radius: 12px; background: linear-gradient(135deg, #667eea, #764ba2); color: white; display: flex; align-items: center; justify-content: center; font-weight: 800; font-size: 16px; margin-right: 15px; }
    .review-form-group input, .review-form-group select, .review-form-group textarea { 
        width: 100%; padding: 12px 16px; border: 2px solid #edf2f7; border-radius: 12px; background: #f8fafc; font-size: 14px; margin-top: 8px;
    }
    .btn-submit-review { background: linear-gradient(135deg, #667eea, #764ba2); color: white; border: none; padding: 14px 28px; border-radius: 12px; font-weight: 800; cursor: pointer; margin-top: 15px; }
    .review-form-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; }
    .review-form-group.full { grid-column: 1 / -1; }
  </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<%
  Product product = (Product) request.getAttribute("product");
  List<ProductVariant> variants = (List<ProductVariant>) request.getAttribute("variants");
  List<Product> relatedProducts = (List<Product>) request.getAttribute("relatedProducts");
%>

<!-- Breadcrumb -->
<div style="padding:14px 40px;font-size:13px;color:var(--text-muted);background:var(--surface);border-bottom:1px solid var(--border);">
  <a href="<%= request.getContextPath() %>/home" style="color:var(--primary);">Home</a> &rsaquo;
  <a href="<%= request.getContextPath() %>/products" style="color:var(--primary);">Products</a> &rsaquo;
  <span><%= product.getProductName() %></span>
</div>

<div class="details-page">
  <div class="details-container">

    <!-- Image -->
    <div class="image-box">
      <img class="main-img" src="<%= request.getContextPath() + "/" + product.getImageUrl() %>" alt="<%= product.getProductName() %>">
    </div>

    <!-- Info -->
    <div class="info-box">
      <div class="product-cat">&#128084; FashionStore Collection</div>
      <h1><%= product.getProductName() %></h1>
      <div class="brand-label">by <strong><%= product.getBrand() != null ? product.getBrand() : "FashionStore" %></strong></div>
      <div class="rating">&#9733;&#9733;&#9733;&#9733;&#9734; <span style="color:var(--text-muted);font-size:13px;">(42 reviews)</span></div>

      <div class="price">&#8377; <%= product.getPrice() %></div>
      <% if (product.getDiscountPercent() > 0) {
           double orig = product.getPrice() / (1 - product.getDiscountPercent()/100.0); %>
      <div class="old-price-row">MRP: &#8377; <%= String.format("%.0f", orig) %></div>
      <div class="discount-text">&#9989; You save <%= (int)product.getDiscountPercent() %>%!</div>
      <% } %>

      <p><%= product.getDescription() %></p>

      <h3 style="font-size:13px;font-weight:700;color:var(--text-muted);text-transform:uppercase;letter-spacing:0.5px;margin-bottom:10px;">Select Size</h3>
      <div class="size-list" id="sizeList">
        <%
          if (variants != null) {
            for (ProductVariant v : variants) {
        %>
        <span class="size-chip" onclick="selectSize(this, '<%= v.getVariantId() %>')"><%= v.getSize() %></span>
        <% } } %>
      </div>

      <form action="<%= request.getContextPath() %>/cart" method="post" onsubmit="return validateForm()">
        <input type="hidden" name="action" value="add">
        <input type="hidden" name="variantId" id="selectedVariant" value="<%= (variants != null && !variants.isEmpty()) ? variants.get(0).getVariantId() : "" %>">

        <div class="qty-row">
          <label>Quantity:</label>
          <input type="number" name="quantity" value="1" min="1" max="10">
        </div>

        <button type="submit" class="add-cart-btn">&#128722; Add to Cart</button>
      </form>

      <div class="info-extras">
        <div class="info-extra-item"><span>&#128230;</span>Free Delivery</div>
        <div class="info-extra-item"><span>&#128260;</span>Easy Returns</div>
        <div class="info-extra-item"><span>&#9989;</span>Authentic</div>
        <div class="info-extra-item"><span>&#128274;</span>Secure Pay</div>
      </div>
    </div>
  </div>

  <!-- Related Products -->
  <% if (relatedProducts != null && !relatedProducts.isEmpty()) { %>
  <div class="related-section">
    <h2>You May Also Like</h2>
    <div class="related-grid">
      <% for (Product rp : relatedProducts) { %>
      <div class="related-card">
        <a href="<%= request.getContextPath() %>/product-details?productId=<%= rp.getProductId() %>">
          <img src="<%= request.getContextPath() + "/" + rp.getImageUrl() %>" alt="<%= rp.getProductName() %>">
          <h4><%= rp.getProductName() %></h4>
          <p>&#8377; <%= rp.getPrice() %></p>
        </a>
      </div>
      <% } %>
    </div>
  </div>
  <% } %>

  <!-- Dummy Reviews Section -->
  <div class="reviews-section">
    <h2><span>&#11088;</span> Customer Reviews</h2>
    
    <div class="review-card">
      <div class="review-header">
        <div class="reviewer-info">
          <div class="reviewer-avatar">JD</div>
          <div>
            <div class="reviewer-name">John Doe</div>
            <div class="review-rating">&#9733;&#9733;&#9733;&#9733;&#9733;</div>
          </div>
        </div>
        <div class="review-date">Oct 12, 2024</div>
      </div>
      <p class="review-content">Absolutely love this! The quality is amazing and it fits perfectly. Would definitely recommend to anyone looking for premium fashion.</p>
    </div>

    <div class="review-card">
      <div class="review-header">
        <div class="reviewer-info">
          <div class="reviewer-avatar">AS</div>
          <div>
            <div class="reviewer-name">Alice Smith</div>
            <div class="review-rating">&#9733;&#9733;&#9733;&#9733;&#9734;</div>
          </div>
        </div>
        <div class="review-date">Sep 28, 2024</div>
      </div>
      <p class="review-content">Great design and color. The material is breathable and comfortable for long hours. Shipping was also faster than expected.</p>
    </div>

    <div class="review-card">
      <div class="review-header">
        <div class="reviewer-info">
          <div class="reviewer-avatar">MK</div>
          <div>
            <div class="reviewer-name">Michael Knight</div>
            <div class="review-rating">&#9733;&#9733;&#9733;&#9733;&#9733;</div>
          </div>
        </div>
        <div class="review-date">Aug 15, 2024</div>
      </div>
      <p class="review-content">I've been wearing this for a few weeks now and it still looks brand new after several washes. Highly durable and stylish!</p>
    </div>

    <!-- Submit Review Form (Dummy) -->
    <div class="add-review-box">
      <h3>Write a Review</h3>
      <form onsubmit="event.preventDefault(); alert('Thank you for your review! (Dummy submission)');">
        <div class="review-form-grid">
          <div class="review-form-group">
            <label>Your Name</label>
            <input type="text" placeholder="Enter your name" required>
          </div>
          <div class="review-form-group">
            <label>Rating</label>
            <select required>
              <option value="5">5 Stars - Excellent</option>
              <option value="4">4 Stars - Good</option>
              <option value="3">3 Stars - Average</option>
              <option value="2">2 Stars - Poor</option>
              <option value="1">1 Star - Terrible</option>
            </select>
          </div>
          <div class="review-form-group full">
            <label>Your Review</label>
            <textarea placeholder="Write your thoughts about the product..." required></textarea>
          </div>
        </div>
        <button type="submit" class="btn-submit-review">Submit Review</button>
      </form>
    </div>
  </div>
</div>

<jsp:include page="/WEB-INF/views/partials/footer.jsp" />
<script>
function selectSize(el, variantId) {
  document.querySelectorAll('.size-chip').forEach(function(c) { c.classList.remove('selected'); });
  el.classList.add('selected');
  document.getElementById('selectedVariant').value = variantId;
}
// Pre-select first size
var first = document.querySelector('.size-chip');
if (first) first.classList.add('selected');

function validateForm() {
  var v = document.getElementById('selectedVariant').value;
  if (!v) { alert('Please select a size!'); return false; }
  return true;
}
</script>
</body>
</html>
