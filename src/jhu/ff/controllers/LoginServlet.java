package jhu.ff.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.UUID;

public class LoginServlet extends HttpServlet {
    private Connection database;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.sqlite.JDBC");
            String path = "jdbc:sqlite:" + getServletContext().getRealPath("/db/jhu-ff.db");
            System.out.println(path);
            database = DriverManager.getConnection(path);
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
            PreparedStatement loginStatement = database.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            loginStatement.setString(1, request.getParameter("username"));
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

            UUID sessionID = UUID.randomUUID();

            PreparedStatement sessionStatement = database.prepareStatement("INSERT INTO user_sessions VALUES (?, ?)");
            sessionStatement.setString(1, request.getParameter("username"));
            sessionStatement.setString(2, sessionID.toString());
            sessionStatement.executeUpdate();

            request.getSession().setAttribute("sessionID", sessionID.toString());

            sessionStatement.close();

            response.sendRedirect("/app");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
