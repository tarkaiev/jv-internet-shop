<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
          crossorigin="anonymous">
    <title>Internet shop</title>
</head>
<body>
<div class = "container-md">
<a href="${pageContext.request.contextPath}/registration">Sign up</a><br>
<a href="${pageContext.request.contextPath}/user/all">All users</a><br>
<a href="${pageContext.request.contextPath}/product/all">All products</a><br>
<a href="${pageContext.request.contextPath}/inject">Put some products to DB</a><br>
<a href="${pageContext.request.contextPath}/logout">Logout</a><br>
</div>
</body>
</html>
