<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Login</title></head>
<body>
    <h2>Login</h2>
    <form action="/login" method="post">
        <label>Username: <input type="text" name="username"></label>
        <label>Password: <input type="password" name="password"></label>
        <input type="submit" value="Login">
    </form>
    <% if(request.getAttribute("error") != null) { %>
        <div class="error-message"><%= request.getAttribute("error") %></div>
    <% } %>
</body>
</html>