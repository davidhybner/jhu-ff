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
        <h2>Register</h2>

        <form action="register" method="post">
            <table>
                <tr>
                    <td>Email Address</td>
                    <td><input type="text" name="emailAddress"></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="password"></td>
                </tr>
                <tr>
                    <td>Password Confirmation</td>
                    <td><input type="password" name="passwordConfirmation"></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="Register"></td>
                </tr>
            </table>
        </form>
    </div>
</div>

<div class="footer"><a href="https://github.com/cxd213/jhu-ff">GitHub</a> <a href="/admin/admin.jsp">Admin Panel</a>
</div>
</body>
</html>