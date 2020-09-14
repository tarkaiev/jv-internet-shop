<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Orders</title>
</head>
<body>
<table border="1">
    <tr>
        <th>Order id</th>
        <th>Products</th>
        <th>User</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.id}"/>
            </td>
            <td>
                <c:out value="${order.products}"/>
            </td>
            <td>
                <c:out value="${order.userId}"/>
            </td>
            <td>
                <form action="${pageContext.request.contextPath}/order/delete" method="get">
                    <input type="hidden" name="orderId" value="${order.id}">
                    <button type="submit">Delete order</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/">Main page</a><br>

</body>
</html>
