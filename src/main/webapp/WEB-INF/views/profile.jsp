<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.fashionstore.model.User" %>
<%
  User profileUser = (User) session.getAttribute("loggedInUser");
  if (profileUser == null) {
    response.sendRedirect(request.getContextPath() + "/login");
    return;
  }
  String initials2 = "?";
  if (profileUser.getFullName() != null && !profileUser.getFullName().isEmpty()) {
    String[] pp = profileUser.getFullName().trim().split("\\s+");
    initials2 = pp.length >= 2
        ? ("" + pp[0].charAt(0) + pp[pp.length-1].charAt(0)).toUpperCase()
        : ("" + pp[0].charAt(0)).toUpperCase();
  }
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>My Profile - FashionStore</title>
  <link rel="icon" type="image/png" href="<%= request.getContextPath() %>/assets/images/favicon.png">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
  <style>
    .profile-page { max-width: 900px; margin: 40px auto; padding: 0 20px; display: grid; grid-template-columns: 280px 1fr; gap: 28px; }
    .profile-sidebar {
      background: var(--surface);
      border-radius: var(--radius);
      border: 1px solid var(--border);
      box-shadow: var(--shadow-sm);
      padding: 30px 24px;
      text-align: center;
      height: fit-content;
    }
    .profile-avatar {
      width: 90px; height: 90px; border-radius: 50%;
      background: linear-gradient(135deg, var(--primary), var(--accent));
      display: flex; align-items: center; justify-content: center;
      font-size: 32px; font-weight: 800; color: white;
      margin: 0 auto 16px;
      box-shadow: 0 8px 24px rgba(106,92,255,0.3);
    }
    .profile-name { font-size: 19px; font-weight: 800; margin-bottom: 4px; }
    .profile-email { font-size: 13px; color: var(--text-muted); margin-bottom: 16px; }
    .profile-badge { display: inline-block; padding: 4px 14px; background: rgba(106,92,255,0.1); color: var(--primary); border-radius: 20px; font-size: 12px; font-weight: 700; }
    .profile-menu { margin-top: 24px; text-align: left; }
    .profile-menu a {
      display: flex; align-items: center; gap: 10px;
      padding: 11px 14px; border-radius: var(--radius-sm);
      font-size: 14px; font-weight: 500; color: var(--text-muted);
      transition: var(--transition); margin-bottom: 4px;
    }
    .profile-menu a:hover, .profile-menu a.active {
      background: rgba(106,92,255,0.08); color: var(--primary);
    }
    .profile-main {
      display: flex; flex-direction: column; gap: 24px;
    }
    .profile-card {
      background: var(--surface);
      border-radius: var(--radius);
      border: 1px solid var(--border);
      box-shadow: var(--shadow-sm);
      padding: 28px;
    }
    .profile-card h3 {
      font-size: 16px; font-weight: 800; margin-bottom: 20px;
      padding-bottom: 12px; border-bottom: 2px solid var(--border);
      display: flex; align-items: center; gap: 8px;
    }
    .info-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 18px; }
    .info-item label { font-size: 11px; font-weight: 700; color: var(--text-muted); text-transform: uppercase; letter-spacing: 0.5px; display: block; margin-bottom: 5px; }
    .info-item span { font-size: 15px; font-weight: 600; color: var(--text); }
    .stat-row { display: flex; gap: 16px; }
    .stat-box { flex: 1; background: var(--surface2); border-radius: var(--radius-sm); padding: 18px; text-align: center; border: 1px solid var(--border); }
    .stat-box .stat-num { font-size: 28px; font-weight: 900; color: var(--primary); }
    .stat-box .stat-label { font-size: 12px; color: var(--text-muted); margin-top: 4px; }
    .logout-btn-card {
      display: flex; align-items: center; justify-content: space-between;
      padding: 18px 24px; background: #fff5f5;
      border: 1px solid rgba(239,68,68,0.2); border-radius: var(--radius-sm);
    }
    @media(max-width:768px) {
      .profile-page { grid-template-columns: 1fr; }
      .info-grid { grid-template-columns: 1fr; }
      .stat-row { flex-direction: column; }
    }

    /* Edit Form Styles */
    .edit-form-card { display: none; }
    .profile-card.editing .display-info { display: none; }
    .profile-card.editing .edit-form-card { display: block; }
    .form-group { margin-bottom: 15px; }
    .form-group label { display: block; font-size: 12px; font-weight: 700; color: var(--text-muted); margin-bottom: 5px; }
    .form-group input { 
      width: 100%; padding: 10px; border: 1px solid var(--border); border-radius: var(--radius-sm);
      font-size: 14px; transition: var(--transition);
    }
    .form-group input:focus { border-color: var(--primary); outline: none; box-shadow: 0 0 0 3px rgba(106,92,255,0.1); }
    .edit-actions { display: flex; gap: 10px; margin-top: 20px; }
    .btn-save { background: var(--primary); color: white; }
    .btn-cancel { background: var(--surface2); color: var(--text); border: 1px solid var(--border); }
  </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<div class="page-header">
  <h1>My Profile</h1>
  <p>Manage your account details and preferences</p>
</div>

<div class="profile-page">

  <div class="profile-sidebar">
    <div class="profile-avatar"><%= initials2 %></div>
    <div class="profile-name"><%= profileUser.getFullName() %></div>
    <div class="profile-email"><%= profileUser.getEmail() %></div>
    <span class="profile-badge">Premium Member</span>
    <div class="profile-menu">
      <a href="<%= request.getContextPath() %>/profile" class="active">&#128100; Profile Info</a>
      <a href="<%= request.getContextPath() %>/wishlist">&#9825; My Wishlist</a>
      <a href="<%= request.getContextPath() %>/order-history">&#128230; My Orders</a>
      <a href="<%= request.getContextPath() %>/cart">&#128722; My Cart</a>
      <a href="<%= request.getContextPath() %>/logout" style="color:var(--danger);">&#128682; Logout</a>
    </div>
  </div>

    <div class="profile-main">
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

      <div class="profile-card">
      <div class="stat-row">
        <div class="stat-box"><div class="stat-num"><%= request.getAttribute("orderCount") != null ? request.getAttribute("orderCount") : "0" %></div><div class="stat-label">Orders Placed</div></div>
        <div class="stat-box"><div class="stat-num"><%= request.getAttribute("wishlistCount") != null ? request.getAttribute("wishlistCount") : "0" %></div><div class="stat-label">Items Wishlist</div></div>
        <div class="stat-box"><div class="stat-num">0</div><div class="stat-label">Reviews Given</div></div>
      </div>
    </div>

    <div class="profile-card">
      <h3>&#128100; Personal Information</h3>
      <div class="info-grid">
        <div class="info-item"><label>Full Name</label><span><%= profileUser.getFullName() %></span></div>
        <div class="info-item"><label>Email Address</label><span><%= profileUser.getEmail() %></span></div>
        <div class="info-item"><label>Phone Number</label><span><%= profileUser.getPhone() != null ? profileUser.getPhone() : "Not provided" %></span></div>
        <div class="info-item"><label>Member Since</label><span><%= profileUser.getCreatedAt() != null ? profileUser.getCreatedAt().toString().substring(0,10) : "N/A" %></span></div>
      </div>
    </div>

    <div class="profile-card" id="address-card">
      <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:20px; padding-bottom:12px; border-bottom:2px solid var(--border);">
        <h3 style="margin-bottom:0; border-bottom:none; padding-bottom:0;">&#128205; Delivery Address</h3>
        <button class="btn btn-sm" onclick="toggleEdit('address-card')" style="padding:4px 12px; font-size:12px;">Edit Address</button>
      </div>
      
      <div class="display-info">
        <div class="info-grid">
          <div class="info-item"><label>Address Line 1</label><span><%= profileUser.getAddressLine1() != null ? profileUser.getAddressLine1() : "-" %></span></div>
          <div class="info-item"><label>Address Line 2</label><span><%= profileUser.getAddressLine2() != null ? profileUser.getAddressLine2() : "-" %></span></div>
          <div class="info-item"><label>City</label><span><%= profileUser.getCity() != null ? profileUser.getCity() : "-" %></span></div>
          <div class="info-item"><label>State</label><span><%= profileUser.getState() != null ? profileUser.getState() : "-" %></span></div>
          <div class="info-item"><label>Pincode</label><span><%= profileUser.getPincode() != null ? profileUser.getPincode() : "-" %></span></div>
          <div class="info-item"><label>Country</label><span><%= profileUser.getCountry() != null ? profileUser.getCountry() : "-" %></span></div>
        </div>
      </div>

      <div class="edit-form-card">
        <form action="<%= request.getContextPath() %>/update-profile" method="post">
          <input type="hidden" name="fullName" value="<%= profileUser.getFullName() %>">
          <input type="hidden" name="phone" value="<%= profileUser.getPhone() != null ? profileUser.getPhone() : "" %>">
          <div class="info-grid">
            <div class="form-group"><label>Address Line 1</label><input type="text" name="addressLine1" value="<%= profileUser.getAddressLine1() != null ? profileUser.getAddressLine1() : "" %>"></div>
            <div class="form-group"><label>Address Line 2</label><input type="text" name="addressLine2" value="<%= profileUser.getAddressLine2() != null ? profileUser.getAddressLine2() : "" %>"></div>
            <div class="form-group"><label>City</label><input type="text" name="city" value="<%= profileUser.getCity() != null ? profileUser.getCity() : "" %>"></div>
            <div class="form-group"><label>State</label><input type="text" name="state" value="<%= profileUser.getState() != null ? profileUser.getState() : "" %>"></div>
            <div class="form-group"><label>Pincode</label><input type="text" name="pincode" value="<%= profileUser.getPincode() != null ? profileUser.getPincode() : "" %>"></div>
            <div class="form-group"><label>Country</label><input type="text" name="country" value="<%= profileUser.getCountry() != null ? profileUser.getCountry() : "" %>"></div>
          </div>
          <div class="edit-actions">
            <button type="submit" class="btn btn-save">Save Address</button>
            <button type="button" class="btn btn-cancel" onclick="toggleEdit('address-card')">Cancel</button>
          </div>
        </form>
      </div>
    </div>

    <div class="profile-card">
      <h3>&#128274; Account Actions</h3>
      <div class="logout-btn-card">
        <div>
          <div style="font-weight:700;font-size:14px;">Sign Out</div>
          <div style="font-size:12px;color:var(--text-muted);">You will be redirected to the login page</div>
        </div>
        <a href="<%= request.getContextPath() %>/logout" class="btn btn-danger">Logout</a>
      </div>
    </div>

  </div>
</div>

<jsp:include page="/WEB-INF/views/partials/footer.jsp" />

<script>
function toggleEdit(cardId) {
    const card = document.getElementById(cardId);
    card.classList.toggle('editing');
}
</script>
</body>
</html>
