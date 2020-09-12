<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All products for admin</title>
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
                <form action="${pageContext.request.contextPath}/product/delete" method="post">
                    <input type="hidden" name="productId" value="${product.id}">
                    <button type="submit" class="btn-danger">Delete product</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/product/add">Add product</a><br>
<a href="${pageContext.request.contextPath}/">Main page</a><br>
</body>
</html>
