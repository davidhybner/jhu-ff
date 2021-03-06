<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JHU-FF</title>
    <link rel="stylesheet" type="text/css" href="/css/app.css">
</head>

<body>

<div class="frame shadow">
    <div class="header">
        <span class="vertically-centered"><h2>JHU Fantasy Football</h2></span>
    </div>

    <div class="nav">
        <ul>
            <li><a href="/leagues?requestType=list">My Leagues</a></li>
            <li><a href="/application/leagues/new.jsp">Create League</a></li>
            <li><a href="/application/leagues/join.jsp">Join League</a></li>
            <li>Account Info</li>
            <li><a href="/logout">Logout</a></li>
        </ul>
    </div>

    <div class="content">
        <h2>Join League</h2>

        <% if(request.getAttribute("message") != null) { %>
            <p class="error-message"><%= request.getAttribute("message") %></p>
        <% } %>

        <p>Enter a league's public identifier value to attempt to join a league.</p>

        <form action="/leagues" method="post">
            <table>
                <tr>
                    <td>League Identifier</td>
                    <td><input type="text" name="leaguePublicId"></td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: right;"><input type="submit" value="Join League"></td>
                </tr>
            </table>
        </form>
    </div>
</div>

<div class="footer"><a href="https://github.com/cxd213/jhu-ff">GitHub</a> <a href="/admin/admin.jsp">Admin Panel</a>
</div>
</body>
</html>