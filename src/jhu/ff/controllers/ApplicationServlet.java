package jhu.ff.controllers;

import jhu.ff.models.Roles;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApplicationServlet extends SecuredController {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/application.jsp");
        requestDispatcher.include(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/application.jsp");
        requestDispatcher.include(request, response);
    }

    @Override
    public String[] getAuthorizedRoles() {
        return new String[]{Roles.Player.getRoleName(), Roles.Admin.getRoleName()};
    }
}