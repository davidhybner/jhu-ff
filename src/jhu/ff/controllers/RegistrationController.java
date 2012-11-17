package jhu.ff.controllers;

import jhu.ff.daos.UserDAO;
import jhu.ff.helpers.ParameterValidator;
import jhu.ff.models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("emailAddress");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("passwordConfirmation");

        if(ParameterValidator.validateParameters(username, password, passwordConfirmation)) {
            if(password.equals(passwordConfirmation)) {
                List<String> roles = new ArrayList<String>();
                roles.add("player");
                boolean createdUser = UserDAO.getInstance().createUser(new User(username, roles), password);

                if(createdUser) {
                    RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/registrationSuccessful.jsp");
                    requestDispatcher.forward(request, response);
                    return;
                }
            }
        }

        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/register.jsp");
        requestDispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/register.jsp");
        requestDispatcher.forward(request, response);
    }
}
