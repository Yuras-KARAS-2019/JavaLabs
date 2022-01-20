<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Receipts</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">
</head>

<body>

<div class="container my-4">
    <jsp:include page="_menu.jsp"></jsp:include>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    <h3>Receipts</h3>

    <c:forEach items="${receipts}" var="receipt">
    <li>ReceiptId: <c:out value="${receipt.id}"/></li>
    <li>EmployeeId: <c:out value="${receipt.employeeId}"/></li>
    <li>Status: <c:out value="${receipt.status}"/></li>

    <table class="table table-sm table-hover">
        <thead>
        <tr class="table-light">
            <th>ProductId</th>
            <th>Quantity</th>
        </tr>
        </thead>
        <c:forEach items="${receipt.receiptProductDtos}" var="product">
        <tbody>
            <tr>
                <td><c:out value="${product.id}"/></td>
                <td><c:out value="${product.quantity}"/></td>
            </tr>
        </tbody>
        </c:forEach>
    </table>
    </c:forEach>
</div>

</body>
</html>