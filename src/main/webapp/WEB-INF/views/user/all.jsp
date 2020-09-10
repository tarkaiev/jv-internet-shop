<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All users</title>
</head>
<body>
<table border="1">
    <tr>
        <th>User id</th>
        <th>Name</th>
        <th>Login</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr>
            <td>
                <c:out value="${user.id}"/>
            </td>
            <td>
                <c:out value="${user.name}"/>
            </td>
            <td>
                <c:out value="${user.login}"/>
            </td>
            <td>
                <form action="${pageContext.request.contextPath}/deleteUser" method="get">
                    <input type="hidden" name="userId" value="${user.id}">
                    <button type="submit">Delete user ${user.login}</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="${pageContext.request.contextPath}/">Main page</a><br>
</body>
</html>
