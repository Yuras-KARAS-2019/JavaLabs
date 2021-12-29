<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Products</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">
</head>

<body>

<div class="container my-2">
    <jsp:include page="_menu.jsp"></jsp:include>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <h3>Products</h3>

    <table class="table table-sm table-hover">
        <thead>
            <tr class="table-light">
                <th>Id</th>
                <th>Name</th>
                <th>AvailableAmount</th>
                <th>Price</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${products}" var="product">
                <tr>
                    <td><c:out value="${product.id}" /></td>
                    <td><c:out value="${product.name}" /></td>
                    <td><c:out value="${product.availableAmount}" /></td>
                    <td><c:out value="${product.price}" /></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>