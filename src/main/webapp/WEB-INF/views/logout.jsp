<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Logged Out - FashionStore</title>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/auth.css">
</head>
<body>
<div class="auth-page">
  <div class="auth-box" style="text-align:center;">
    <div style="font-size:60px;margin-bottom:16px;">&#128075;</div>
    <h2 style="font-size:24px;font-weight:900;margin-bottom:8px;">See You Soon!</h2>
    <p style="color:var(--text-muted);margin-bottom:28px;">You have been successfully logged out of your FashionStore account.</p>
    <div style="display:flex;gap:12px;justify-content:center;flex-wrap:wrap;">
      <a href="<%= request.getContextPath() %>/login" class="btn btn-primary">Sign In Again</a>
      <a href="<%= request.getContextPath() %>/home" class="btn btn-outline">Back to Home</a>
    </div>
  </div>
</div>
</body>
</html>
