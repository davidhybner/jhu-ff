package jhu.ff.controllers;

import jhu.ff.models.Roles;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UsersController extends SecuredController {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public String[] getAuthorizedRoles() {
        return new String[]{Roles.Admin.getRoleName()};
    }
}
