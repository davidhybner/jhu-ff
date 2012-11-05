package jhu.ff.controllers;

import jhu.ff.models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginController extends HttpServlet {
    private Connection database;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            database = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/jhu_ff", "root", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        try {
            database.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!request.getParameterMap().containsKey("username") || !request.getParameterMap().containsKey("password")) {
            RequestDispatcher requestDispatcher = request.getServletContext().getRequestDispatcher("/login.jsp");
            request.setAttribute("error", "must enter username and password!");
            requestDispatcher.forward(request, response);
            return;
        }

        try {
            String username = request.getParameter("username");

            PreparedStatement loginStatement = database.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            loginStatement.setString(1, username);
            loginStatement.setString(2, request.getParameter("password"));
            ResultSet users = loginStatement.executeQuery();

            if(!users.next()) {
                RequestDispatcher requestDispatcher = request.getServletContext().getRequestDispatcher("/login.jsp");
                request.setAttribute("error", "credentials were invalid!");
                requestDispatcher.forward(request, response);
                return;
            }

            loginStatement.close();
            users.close();

            PreparedStatement rolesStatement = database.prepareStatement("SELECT * FROM user_roles WHERE username = ?");
            rolesStatement.setString(1, username);
            ResultSet userRolesResults = rolesStatement.executeQuery();

            List<String> roles = new ArrayList<String>();

            while (userRolesResults.next()) {
                roles.add(userRolesResults.getString("role"));
            }

            User user = new User(username, roles);

            request.getSession().setAttribute("user", user);

            if(user.hasRole("admin")) {
                response.sendRedirect("/views/admin/admin.jsp");
            } else if(user.hasRole("player")) {
                response.sendRedirect("/app");
            } else {
                response.sendRedirect("/views/error.jsp");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
