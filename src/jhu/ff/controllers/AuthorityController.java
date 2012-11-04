package jhu.ff.controllers;

import jhu.ff.models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AuthorityController extends HttpServlet {
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
        User loggedInUser = (User) request.getSession().getAttribute("user");

        if (loggedInUser != null) {
            for(String role : getAuthorizedRoles()) {
                if(loggedInUser.hasRole(role)) {
                    return true;
                }
            }
        }

        return false;
    }

    public abstract String[] getAuthorizedRoles();

    protected final Connection getConnection() {
        return database;
    }
}
