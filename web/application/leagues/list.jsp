<%@ page import="jhu.ff.models.League" %>
<%@ page import="java.util.List" %>
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
        List<League> leagues = (List<League>) request.getAttribute("leagues");
    %>

    <div class="content">
        <h2>My Leagues</h2>
        <ul>
            <% for(League league : leagues) { %>
                <% if(league.getOwner().equals(request.getUserPrincipal().getName())) { %>
                    <li><a href="/leagues?requestType=show&leagueId=<%= league.getId() %>"><%= league.getName() + " (owner)" %></a></li>
                <% } else { %>
                    <li><a href="/leagues?requestType=show&leagueId=<%= league.getId() %>"><%= league.getName() %></a></li>
                <% } %>
            <% } %>
        </ul>
    </div>
</div>

<div class="footer"><a href="https://github.com/cxd213/jhu-ff">GitHub</a> <a href="/admin/admin.jsp">Admin Panel</a>
</div>
</body>
</html>