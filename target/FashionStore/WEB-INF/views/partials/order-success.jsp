<!DOCTYPE html>
<html>
<head>
    <title>Order Success</title>

    <style>
        .success-box {
            text-align: center;
            margin-top: 100px;
        }

        .success-box h2 {
            color: green;
        }

        .success-box a {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background: #6a5cff;
            color: white;
            text-decoration: none;
            border-radius: 8px;
        }
    </style>
</head>
<body>

<div class="success-box">
    <h2>🎉 Order Placed Successfully!</h2>
    <a href="<%= request.getContextPath() %>/home">Go to Home</a>
</div>

</body>
</html>