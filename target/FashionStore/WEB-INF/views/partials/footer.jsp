<%@ page import="com.fashionstore.model.User" %>
<footer class="footer">
  <div class="footer-top">
    <div class="footer-brand">
      <div class="footer-logo">FashionStore</div>
      <p>Your one-stop destination for premium fashion. Curated collections for every style and occasion.</p>
      <div class="footer-social">
        <a href="#">FB</a><a href="#">IG</a><a href="#">TW</a>
      </div>
    </div>
    <div class="footer-section">
      <h4>Shop</h4>
      <a href="<%= request.getContextPath() %>/products">All Products</a>
      <a href="<%= request.getContextPath() %>/products?categoryId=1">Mens Fashion</a>
      <a href="<%= request.getContextPath() %>/products?categoryId=2">Womens Fashion</a>
      <a href="<%= request.getContextPath() %>/products?categoryId=3">Kids Fashion</a>
      <a href="<%= request.getContextPath() %>/products?categoryId=4">Accessories</a>
    </div>
    <div class="footer-section">
      <h4>Account</h4>
      <a href="<%= request.getContextPath() %>/profile">My Profile</a>
      <a href="<%= request.getContextPath() %>/order-history">My Orders</a>
      <a href="<%= request.getContextPath() %>/cart">Shopping Cart</a>
      <a href="<%= request.getContextPath() %>/login">Login</a>
      <a href="<%= request.getContextPath() %>/register">Register</a>
    </div>
    <div class="footer-section footer-newsletter">
      <h4>Newsletter</h4>
      <p style="color:rgba(255,255,255,0.5);font-size:13px;margin-bottom:14px;">Get the latest deals and trends in your inbox.</p>
      <input type="email" placeholder="your@email.com" />
      <button type="button">Subscribe</button>
    </div>
  </div>
  <div class="footer-bottom">&copy; 2025 FashionStore &middot; All Rights Reserved &middot; Made for Fashion Lovers</div>
</footer>
