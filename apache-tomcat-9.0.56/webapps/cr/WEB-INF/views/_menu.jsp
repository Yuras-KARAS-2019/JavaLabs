<a href="${pageContext.request.contextPath}/api/v0/products?isFrontend=true" class="btn btn-secondary btn-sm">
   Products
</a>

<a href="${pageContext.request.contextPath}/userInfo" class="btn btn-info btn-sm">
    User Info
</a>

<a href="${pageContext.request.contextPath}/api/v0/receipts?isFrontend=true" class="btn btn-success btn-sm">
    Receipts
</a>

<a href="${pageContext.request.contextPath}/logout" class="btn btn-warning btn-sm">
    Logout
</a>

<span class="badge badge-danger">worker: [${loggedInUser.login}]</span>

