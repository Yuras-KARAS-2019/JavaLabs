<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">
</head>
<body>

<div class="container my-2">
    <h3>Login Page</h3>

    <p style="color: red;">${errorString}</p>

    <form method="POST" action="${pageContext.request.contextPath}/login" class="form-signin">
        <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
        <input type="hidden" name="redirectId" value="${param.redirectId}" />

        <label for="login" class="sr-only">Login</label>
        <input value="${user.login}" name="login" type="text" id="login" class="form-control" placeholder="Login" required="">

        <br />

        <label for="password" class="sr-only">Password</label>
        <input value="${user.password}" name="password" type="password" id="password" class="form-control" placeholder="Password" required="">

        <br />
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        <a class="btn btn-lg btn-danger btn-block" href="${pageContext.request.contextPath}/">Cancel</a>
    </form>

</div>

</body>
</html>