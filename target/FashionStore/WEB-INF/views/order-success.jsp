<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Order Placed! - FashionStore</title>
  <link rel="icon" type="image/png" href="<%= request.getContextPath() %>/assets/images/favicon.png">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/auth.css">
  <style>
    .success-icon { font-size: 72px; animation: pop 0.5s ease; }
    @keyframes pop { 0%{transform:scale(0.5);opacity:0} 100%{transform:scale(1);opacity:1} }
    .confetti { position:fixed;top:0;left:0;width:100%;height:100%;pointer-events:none;z-index:9999; }
    .confetti span {
      position:absolute;display:block;width:10px;height:10px;
      background:var(--accent);border-radius:2px;
      animation:fall 3s linear infinite;
    }
    @keyframes fall {
      0%{transform:translateY(-20px) rotate(0deg);opacity:1}
      100%{transform:translateY(100vh) rotate(360deg);opacity:0}
    }
  </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<div class="confetti" id="confetti"></div>

<div class="auth-page" style="min-height:80vh;">
  <div class="auth-box" style="text-align:center;">
    <div class="success-icon">&#127881;</div>
    <h2 style="font-size:26px;font-weight:900;margin:16px 0 8px;color:var(--success);">Order Placed Successfully!</h2>
    <p style="color:var(--text-muted);margin-bottom:28px;">Thank you for shopping with FashionStore! Your order is being prepared and will be delivered soon.</p>

    <div style="background:var(--surface2);border-radius:var(--radius-sm);padding:16px;margin-bottom:24px;border:1px solid var(--border);">
      <div style="font-size:13px;color:var(--text-muted);">Order ID</div>
      <div style="font-size:20px;font-weight:900;color:var(--primary);">#FS<%= System.currentTimeMillis() % 100000 %></div>
    </div>

    <div style="display:flex;gap:12px;justify-content:center;flex-wrap:wrap;">
      <a href="<%= request.getContextPath() %>/order-history" class="btn btn-primary">&#128230; View My Orders</a>
      <a href="<%= request.getContextPath() %>/products" class="btn btn-outline">Continue Shopping</a>
    </div>
  </div>
</div>

<jsp:include page="/WEB-INF/views/partials/footer.jsp" />
<script>
var conf = document.getElementById('confetti');
var colors = ['#6a5cff','#ff6b9d','#ffb347','#22c55e','#38bdf8'];
for (var i = 0; i < 40; i++) {
  var s = document.createElement('span');
  s.style.left = Math.random()*100 + '%';
  s.style.top = Math.random()*20 + 'px';
  s.style.background = colors[Math.floor(Math.random()*colors.length)];
  s.style.animationDelay = Math.random()*3 + 's';
  s.style.animationDuration = (2 + Math.random()*2) + 's';
  s.style.width = s.style.height = (6 + Math.random()*8) + 'px';
  conf.appendChild(s);
}
setTimeout(function(){ conf.style.display='none'; }, 5000);
</script>
</body>
</html>
