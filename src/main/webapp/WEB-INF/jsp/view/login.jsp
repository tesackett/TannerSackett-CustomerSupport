

<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<p> Please Log in</p>
<form action="login" method="post">
    <label for="username">Username:</label><br>
    <input type="text" id="username" name="username"><br>
    <label for="password">Password:</label><br>
    <input type="password" id="password" name="password"><br><br>
    <input type="submit" value="Login">
</form>
<% if (request.getAttribute("loginFailed") != null && (boolean) request.getAttribute("loginFailed")) { %>
<p>Login failed. Please check your username and password.</p>
<% } %>
</body>
</html>
