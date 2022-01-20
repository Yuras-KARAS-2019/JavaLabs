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

<div class="container my-3">
    <p style="color: red;">${errorString}</p>
    <h1 class="h3 mb-3   font-weight-normal">Registration page</h1>
    <form method="POST" action="${pageContext.request.contextPath}/registration">
        <br/>
        <label for="name" class="sr-only">Name</label>
        <input value="${user.name}" name="name" type="text" id="name" class="form-control"
               placeholder="name" required="">
        <br/>
        <select>value="${user.role}" name="role" type="text" id="role" class="form-reg">
            <option>cashier</option>
            <option>senior cashier</option>
            <option>merchandise expert</option>
        </select>
        <br/>
        <br/>
        <label for="login" class="sr-only">Login</label>
        <input value="${user.login}" name="login" type="text" id="login" class="form-control" placeholder="Login"
               required="">
        <br/>
        <label for="password" class="sr-only">Password</label>
        <input value="${user.password}" name="password" type="password" id="password" class="form-control"
               placeholder="Password" required="">
        <br/>
        <button class="btn btn-lg btn-success btn-block" type="submit">Registration</button>
    </form>

</div>

</body>
</html>