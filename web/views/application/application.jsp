<%@ page import="jhu.ff.models.Roles" %>
<%@ page import="jhu.ff.models.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>JHU-FF | Welcome</title></head>
<body>

    <%
        User user = (User) request.getSession().getAttribute("user");
    %>

    <h1>JHU-FF</h1>
    <p>Welcome to JHU-FF, <%= user.getUsername() %>!</p>

    <% if(user.hasRole(Roles.Player.getRoleName())) { %>
        <p>Player</p>
    <% } %>

    <% if(user.hasRole(Roles.Admin.getRoleName())) { %>
        <p>Admin</p>
    <% } %>

    <form action="/logout" method="get">
        <input type="submit" value="Logout">
    </form>
</body>
</html>