<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Info</title>
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">
</head>
<body>
<div class="container my-2">
    <jsp:include page="_menu.jsp"></jsp:include>

    <h3>Hello: ${loggedInUser.name}</h3>

    Id: <b>${loggedInUser.id}</b>
    <br />
    Name: <b>${loggedInUser.name}</b>
    <br />
    Role: ${loggedInUser.role}
    <br/>
    Login: ${loggedInUser.login}
    <br/>
</div>
</body>
</html>