<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping cart</title>
</head>
<body>
<table border="1">
    <tr>
        <th>Product id</th>
        <th>Name</th>
        <th>Price</th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>
                <c:out value="${product.id}"/>
            </td>
            <td>
                <c:out value="${product.name}"/>
            </td>
            <td>
                <c:out value="${product.price}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/cart/delete?id=${product.id}">
                    Delete from the cart</a>
            </td>
        </tr>
    </c:forEach>
    <tr>
        <td colspan="2">
            <form action="${pageContext.request.contextPath}/order/complete" method="post">
                <input type="hidden" name="shoppingCartId" value="${shoppingCartId}">
                <button type="submit">Create order</button>
        </td>
    </tr>
</table>
<a href="${pageContext.request.contextPath}/registration">Sign up</a><br>
<a href="${pageContext.request.contextPath}/user/all">All users</a><br>
<a href="${pageContext.request.contextPath}/product/all">All products</a><br>
<a href="${pageContext.request.contextPath}/">Main page</a><br>
</body>
</html>
