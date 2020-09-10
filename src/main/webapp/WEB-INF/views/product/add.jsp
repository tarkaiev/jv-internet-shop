<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add product</title>
</head>
<body>
<h1>Add new product</h1>
<h4 style="color: blue">${message}</h4>
<form method="post" action="${pageContext.request.contextPath}/product/add">
    <table>
        <tr>
            <td>
                <label>Name</label>
            </td>
            <td>
                <input type="text" name="name">
            </td>
        </tr>
        <tr>
            <td>
                <label>Price</label>
            </td>
            <td>
                <input type="text" name="price">
            </td>
        </tr>
        <tr>
            <td rowspan="2">
                <button type="submit">Add product</button>
            </td>
        </tr>
    </table>
</form>
<a href="${pageContext.request.contextPath}/">Main page</a><br>
<a href="${pageContext.request.contextPath}/user/all">All user</a><br>
<a href="${pageContext.request.contextPath}/product/all">All products</a><br>
<a href="${pageContext.request.contextPath}/product/add">Add product</a><br>
</body>
</html>
