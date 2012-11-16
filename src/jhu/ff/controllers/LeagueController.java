package jhu.ff.controllers;

import jhu.ff.daos.LeagueDAO;
import jhu.ff.models.League;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LeagueController extends HttpServlet {
    private LeagueDAO leagueDAO = LeagueDAO.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String leagueName = request.getParameter("leagueName");
        String owner = request.getUserPrincipal().getName();

        int leagueID = leagueDAO.createLeague(leagueName, owner);

        if(leagueID > -1) {
            League league = leagueDAO.getLeague(leagueID);
            request.setAttribute("league", league);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/application/leagues/create.jsp");
            requestDispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
