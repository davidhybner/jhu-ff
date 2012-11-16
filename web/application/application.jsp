<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JHU-FF | Welcome</title>
    <link type="text/css" rel="stylesheet" href="css/app.css">
</head>
<body>

<div class="header">
    <h2>JHU-FF Application</h2>

    <form action="/logout" method="get">
        <input type="submit" value="Logout">
    </form>
</div>
<div class="nav">
    <ul>
        <li>My Leagues</li>
        <li><a href="leagues/new.jsp">Create League</a></li>
        <li>Join League</li>
        <li>Account Info</li>
    </ul>
</div>

<p>
    Welcome to JHU-FF!
</p>
</body>
</html>