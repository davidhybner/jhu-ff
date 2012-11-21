<%@ page import="jhu.ff.models.League" %>
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

    <%
        League league = (League) request.getAttribute("league");
    %>

    <div class="content">
        <h2><%= league.getName() %>
        </h2>

        <div class="sub-nav">
            <ul>
                <li class="sub-nav-button">League Home</li>
                <li class="sub-nav-button">My Team</li>
                <li class="sub-nav-button">Standings</li>
                <li class="sub-nav-button">Schedule</li>
            </ul>
        </div>


        <p><%= league.getPlayerRosters() %>
        </p>
    </div>
</div>

<div class="footer"><a href="https://github.com/cxd213/jhu-ff">GitHub</a> <a href="/admin/admin.jsp">Admin Panel</a>
</div>
</body>
</html>