package jhu.ff.controllers;

import jhu.ff.daos.LeagueDAO;
import jhu.ff.daos.LeaguePlayerDAO;
import jhu.ff.models.League;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LeagueController extends HttpServlet {
    private LeagueDAO leagueDAO = LeagueDAO.getInstance();
    private LeaguePlayerDAO leaguePlayerDAO = LeaguePlayerDAO.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("leagueName") != null) {
            String leagueName = request.getParameter("leagueName");
            String owner = request.getUserPrincipal().getName();

            int leagueID = leagueDAO.createLeague(leagueName, owner);

            if (leagueID > -1) {
                League league = leagueDAO.getLeague(leagueID);
                request.setAttribute("league", league);
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/application/leagues/create.jsp");
                requestDispatcher.forward(request, response);
            }
        } else if (request.getParameter("leaguePublicId") != null) {
            String message = "";
            String leaguePublicId = request.getParameter("leaguePublicId");

            League leagueToJoin = leagueDAO.findLeagueByPublicId(leaguePublicId);
            boolean playerAdded = false;

            if(leagueToJoin == null) {
                message += "League not found. ";
            } else {
                playerAdded = leaguePlayerDAO.addPlayerToLeague(leagueToJoin.getId(), request.getUserPrincipal().getName());
            }

            if(playerAdded) {
                message += "You have successfully been added to the league!";
            } else {
                message += "Unable to join the league.";
            }

            request.setAttribute("message", message);

            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/application/leagues/join.jsp");
            requestDispatcher.forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
