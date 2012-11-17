<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>JHU-FF</title>
    <link rel="stylesheet" type="text/css" href="css/app.css">
</head>

<body>

<div class="frame shadow">
    <div class="header">
        <span class="vertically-centered"><h2>JHU Fantasy Football</h2></span>
    </div>

    <div class="content">
        <h2>Registration Successful!</h2>

        <h3>Welcome, <%= request.getParameter("emailAddress") %>, to JHU Fantasy Football!</h3>

        <div style="text-align: center;"><a href="/app"><span class="play-button">Get in the Game!</span></a></div>

    </div>
</div>

<div class="footer"><a href="https://github.com/cxd213/jhu-ff">GitHub</a> <a href="/admin/admin.jsp">Admin Panel</a>
</div>
</body>
</html>