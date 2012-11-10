package jhu.ff.controllers;

import jhu.ff.helpers.ConnectionPool;
import jhu.ff.models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AuthorityController extends HttpServlet {

    protected ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ConnectionPool.getInstance();
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
}
