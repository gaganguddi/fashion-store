<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.fashionstore.model.WishlistItem" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Wishlist - FashionStore</title>
    <link rel="icon" type="image/png" href="<%= request.getContextPath() %>/assets/images/favicon.png">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
    <style>
        .wishlist-container { max-width: 1200px; margin: 40px auto; padding: 0 20px; }
        .wishlist-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 30px; }
        .wishlist-card { 
            background: var(--surface); 
            border-radius: var(--radius); 
            border: 1px solid var(--border); 
            overflow: hidden; 
            transition: var(--transition);
            position: relative;
        }
        .wishlist-card:hover { transform: translateY(-5px); box-shadow: var(--shadow-md); }
        .wishlist-img { width: 100%; height: 320px; object-fit: cover; }
        .wishlist-info { padding: 20px; }
        .wishlist-name { font-size: 18px; font-weight: 700; margin-bottom: 8px; }
        .wishlist-price { font-size: 16px; font-weight: 800; color: var(--primary); margin-bottom: 15px; }
        .wishlist-actions { display: flex; gap: 10px; }
        .remove-wishlist { 
            position: absolute; top: 15px; right: 15px; 
            background: white; border: none; width: 35px; height: 35px; 
            border-radius: 50%; display: flex; align-items: center; justify-content: center;
            cursor: pointer; box-shadow: var(--shadow-sm); color: var(--danger);
            font-size: 18px; transition: var(--transition);
        }
        .remove-wishlist:hover { background: var(--danger); color: white; }
        .empty-wishlist { text-align: center; padding: 100px 20px; }
        .empty-wishlist i { font-size: 80px; color: var(--border); margin-bottom: 20px; display: block; }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<div class="page-header">
    <h1>My Wishlist</h1>
    <p>Save your favorite items for later</p>
</div>

<div class="wishlist-container">
    <%
        List<WishlistItem> items = (List<WishlistItem>) request.getAttribute("wishlistItems");
        if (items != null && !items.isEmpty()) {
    %>
    <div class="wishlist-grid">
        <% for (WishlistItem item : items) { %>
        <div class="wishlist-card" id="wishlist-item-<%= item.getProductId() %>">
            <button class="remove-wishlist" onclick="removeFromWishlist(<%= item.getProductId() %>)" title="Remove from wishlist">&times;</button>
            <a href="<%= request.getContextPath() %>/product-details?productId=<%= item.getProductId() %>">
                <img src="<%= request.getContextPath() %>/<%= item.getImageUrl() %>" alt="<%= item.getProductName() %>" class="wishlist-img">
            </a>
            <div class="wishlist-info">
                <div class="wishlist-name"><%= item.getProductName() %></div>
                <div class="wishlist-price">&#8377; <%= item.getPrice() %></div>
                <div class="wishlist-actions">
                    <a href="<%= request.getContextPath() %>/product-details?productId=<%= item.getProductId() %>" class="btn btn-primary" style="flex:1; text-align:center;">Select Options</a>
                </div>
            </div>
        </div>
        <% } %>
    </div>
    <% } else { %>
    <div class="empty-wishlist">
        <div style="font-size: 80px; margin-bottom: 20px;">&#9825;</div>
        <h2>Your wishlist is empty</h2>
        <p style="margin: 15px 0 30px; color: var(--text-muted);">Explore our collection and save items you love!</p>
        <a href="<%= request.getContextPath() %>/products" class="btn btn-primary">Start Shopping</a>
    </div>
    <% } %>
</div>

<jsp:include page="/WEB-INF/views/partials/footer.jsp" />

<script>
function removeFromWishlist(productId) {
    if(!confirm('Remove this item from your wishlist?')) return;
    
    fetch('<%= request.getContextPath() %>/wishlist?action=remove&productId=' + productId, {
        method: 'POST'
    })
    .then(response => response.text())
    .then(data => {
        if (data === 'success') {
            const el = document.getElementById('wishlist-item-' + productId);
            el.style.opacity = '0';
            el.style.transform = 'scale(0.8)';
            setTimeout(() => {
                el.remove();
                if (document.querySelectorAll('.wishlist-card').length === 0) {
                    location.reload();
                }
            }, 300);
        } else {
            alert('Error removing item from wishlist');
        }
    });
}
</script>
</body>
</html>
