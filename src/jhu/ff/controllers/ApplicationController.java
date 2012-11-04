package jhu.ff.controllers;

import jhu.ff.models.Roles;
import jhu.ff.models.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApplicationController extends AuthorityController {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
        User loggedInUser = (User) request.getSession().getAttribute("user");
        response.sendRedirect("/views/application/application.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public String[] getAuthorizedRoles() {
        return new String[]{Roles.Player.getRoleName()};
    }
}