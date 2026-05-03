<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.fashionstore.model.User" %>
<%
  User checkUser = (User) session.getAttribute("loggedInUser");
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Checkout - FashionStore</title>
  <link rel="icon" type="image/png" href="<%= request.getContextPath() %>/assets/images/favicon.png">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/css/style.css">
  <style>
    .checkout-wrap { max-width: 680px; margin: 50px auto; padding: 0 20px; }
    .checkout-card {
      background: var(--surface);
      border-radius: 20px;
      border: 1px solid var(--border);
      box-shadow: var(--shadow-md);
      overflow: hidden;
    }
    .checkout-top {
      background: linear-gradient(135deg, var(--primary), var(--primary-light));
      padding: 30px 36px;
      color: white;
    }
    .checkout-top h2 { font-size: 24px; font-weight: 900; margin-bottom: 4px; }
    .checkout-top p { opacity: 0.8; font-size: 14px; }
    .checkout-body { padding: 30px 36px; }
    .delivery-info {
      background: var(--surface2);
      border-radius: var(--radius-sm);
      border: 1px solid var(--border);
      padding: 18px; margin-bottom: 24px;
    }
    .delivery-info h4 { font-size: 13px; font-weight: 700; color: var(--text-muted); text-transform: uppercase; letter-spacing: 0.5px; margin-bottom: 10px; }
    .delivery-info p { font-size: 14px; font-weight: 500; line-height: 1.7; }
    .payment-opts { margin-bottom: 24px; }
    .payment-opts h4 { font-size: 13px; font-weight: 700; color: var(--text-muted); text-transform: uppercase; letter-spacing: 0.5px; margin-bottom: 12px; }
    .pay-opt {
      display: flex; align-items: center; gap: 12px;
      padding: 14px 16px; border: 2px solid var(--border);
      border-radius: var(--radius-sm); margin-bottom: 8px;
      cursor: pointer; transition: var(--transition); font-size: 14px; font-weight: 500;
    }
    .pay-opt:hover, .pay-opt.selected { border-color: var(--primary); background: rgba(106,92,255,0.04); }
    .place-btn {
      display: block; width: 100%; padding: 16px;
      background: linear-gradient(135deg, var(--primary), var(--accent));
      color: white; border: none; border-radius: var(--radius-sm);
      font-size: 17px; font-weight: 800; cursor: pointer;
      box-shadow: 0 4px 20px rgba(106,92,255,0.4); transition: var(--transition);
    }
    .place-btn:hover { transform: translateY(-2px); box-shadow: 0 8px 30px rgba(106,92,255,0.5); }
  </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/partials/navbar.jsp" />

<div class="checkout-wrap">
  <div class="checkout-card">
    <div class="checkout-top">
      <h2>&#128274; Secure Checkout</h2>
      <p>Review your order and complete payment safely</p>
    </div>
    <div class="checkout-body">

      <% if (checkUser != null) { %>
      <div class="delivery-info">
        <h4>&#128205; Delivery Address</h4>
        <p>
          <strong><%= checkUser.getFullName() %></strong><br>
          <%= checkUser.getAddressLine1() != null ? checkUser.getAddressLine1() : "" %>
          <%= checkUser.getAddressLine2() != null && !checkUser.getAddressLine2().isEmpty() ? ", " + checkUser.getAddressLine2() : "" %><br>
          <%= checkUser.getCity() != null ? checkUser.getCity() : "" %>,
          <%= checkUser.getState() != null ? checkUser.getState() : "" %> -
          <%= checkUser.getPincode() != null ? checkUser.getPincode() : "" %><br>
          &#128222; <%= checkUser.getPhone() != null ? checkUser.getPhone() : "" %>
        </p>
      </div>
      <% } %>

      <div class="payment-opts">
        <h4>&#128179; Payment Method</h4>
        <div class="pay-opt selected" onclick="selectPay(this)">&#128179; Cash on Delivery (COD)</div>
        <div class="pay-opt" onclick="selectPay(this)">&#128184; UPI / QR Code</div>
        <div class="pay-opt" onclick="selectPay(this)">&#128184; Credit / Debit Card</div>
      </div>

      <form action="<%= request.getContextPath() %>/place-order" method="post">
        <button type="submit" class="place-btn">&#9989; Place Order Now</button>
      </form>

      <div style="font-size:12px;color:var(--text-muted);text-align:center;margin-top:16px;">
        &#128274; Secured by SSL encryption &bull; 100% Safe & Trusted
      </div>
    </div>
  </div>
</div>

<jsp:include page="/WEB-INF/views/partials/footer.jsp" />
<script>
function selectPay(el) {
  document.querySelectorAll('.pay-opt').forEach(function(o){o.classList.remove('selected');});
  el.classList.add('selected');
}
</script>
</body>
</html>
