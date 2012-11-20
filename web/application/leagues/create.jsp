<%@ page import="jhu.ff.models.League" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JHU-FF | League Created</title>
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
        <h2>League Created!</h2>

        <%
            League league = (League) request.getAttribute("league");
        %>

        <p>League <%= league.getName() %> created successfully! The league's public identifier
            is: <%= league.getPublicID() %> -
            share this identifier with your friends so they can join the league!</p>

        <% String leagueURL = "/leagues/" + league.getId(); %>

        <div style="text-align: center;"><span class="play-button"><a href="<%= leagueURL %>">Goto the League!</a></span></div>
    </div>
</div>

<div class="footer"><a href="https://github.com/cxd213/jhu-ff">GitHub</a> <a href="/admin/admin.jsp">Admin Panel</a>
</div>
</body>
</html>