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

public abstract class SecuredController extends HttpServlet {
    private Connection database;

    @Override
    public void init() throws ServletException {
        super.init();

        try {
            Class.forName("org.sqlite.JDBC");
            database = DriverManager.getConnection("jdbc:sqlite:" + getServletContext().getRealPath("/db/jhu-ff.db"));
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

        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!isUserAuthorized(req)) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!isUserAuthorized(req)) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    private boolean isUserAuthorized(HttpServletRequest request) {
        String sessionID = (String) request.getSession().getAttribute("sessionID");

        try {
            PreparedStatement statement = database.prepareStatement("SELECT * FROM user_sessions WHERE session_id = ?");
            statement.setString(1, sessionID);
            ResultSet userSessionsResults = statement.executeQuery();

            String username = "";

            if(userSessionsResults.next()) {
                username = userSessionsResults.getString("username");
            } else {
                return false;
            }

            userSessionsResults.close();
            statement.close();

            statement = database.prepareStatement("SELECT * FROM user_roles WHERE username = ?");
            statement.setString(1, username);
            ResultSet userRolesResults = statement.executeQuery();

            List<String> roles = new ArrayList<String>();

            while (userRolesResults.next()) {
                roles.add(userRolesResults.getString("role"));
            }

            User user = new User(username, roles);

            userRolesResults.close();
            statement.close();

            for(String role : getAuthorizedRoles()) {
                if(user.hasRole(role)) {
                    request.getSession().setAttribute("userRoles", roles);
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public abstract String[] getAuthorizedRoles();

    protected final Connection getConnection() {
        return database;
    }
}
