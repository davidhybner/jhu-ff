<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JHU-FF | Create League</title>
    <link rel="stylesheet" type="text/css" href="/application/css/app.css">
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

<h3>Create League</h3>

<form action="/leagues" method="post">
    <table>
        <tr>
            <td>League Name</td>
            <td><input type="text" name="leagueName"/></td>
        </tr>
        <tr>
            <td>Invite Friends</td>
            <td><textarea name="friendList" rows="5" cols="30"></textarea></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Create League!"/></td>
        </tr>
    </table>
</form>
</body>
</html>