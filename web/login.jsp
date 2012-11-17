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
        <h2>Login</h2>

        <form method="POST" action="j_security_check">
            <table>
                <tr>
                    <td>Email Address</td>
                    <td><input type="text" name="j_username"/></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="j_password"/></td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: right;"><input type="submit" value="Play!"/></td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center;">Not registered? <a href="/reigster">Sign up now!</a></td>
                </tr>
            </table>
        </form>
    </div>
</div>

<div class="footer"><a href="https://github.com/cxd213/jhu-ff">GitHub</a> <a href="/admin/admin.jsp">Admin Panel</a>
</div>
</body>
</html>