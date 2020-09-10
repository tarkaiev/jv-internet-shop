<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All products:</title>
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
                <a href="/product/buy?id=${product.id}">Add to cart</a>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/current/cart">Show cart</a><br>
<a href="${pageContext.request.contextPath}/product/add">Add product</a><br>
<a href="${pageContext.request.contextPath}/user/all">All users</a><br>
<a href="${pageContext.request.contextPath}/">Main page</a><br>
</body>
</html>
