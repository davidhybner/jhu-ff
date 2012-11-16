<%@ page import="jhu.ff.models.League" %>
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

<%
    League league = (League) request.getAttribute("league");
%>

<h3>League Created Successfully!</h3>

<p>League <%= league.getName() %> created successfully! The league's public identifier is: <%= league.getPublicID() %> -
    share this identifier with your friends so they can join the league!</p>

<% String leagueURL = "/leagues/" + league.getId(); %>

<h3><a href="<%= leagueURL %>">Goto the League!</a></h3>


</body>
</html>