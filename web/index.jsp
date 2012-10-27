<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head><title>JHU-FF</title></head>
  <body>
    <h1>JHU-FF</h1>

    <p>Welcome to JHU-FF!</p>

    <h2>Login</h2>
    <form action="/login" method="post">
        <label>Username: <input type="text" name="username"></label>
        <label>Password: <input type="password" name="password"></label>
        <input type="submit" value="Login">
    </form>
  </body>
</html>