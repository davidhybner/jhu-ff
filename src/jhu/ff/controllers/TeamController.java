package jhu.ff.controllers;

import jhu.ff.daos.LeagueDAO;
import jhu.ff.daos.LeaguePlayerDAO;
import jhu.ff.daos.SeasonDAO;
import jhu.ff.models.League;
import jhu.ff.models.SeasonWeek;
import jhu.ff.models.Team;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TeamController extends HttpServlet {
    private LeaguePlayerDAO leaguePlayerDAO = LeaguePlayerDAO.getInstance();
    private LeagueDAO leagueDAO = LeagueDAO.getInstance();
    private SeasonDAO seasonDAO = SeasonDAO.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int leagueId = Integer.parseInt(request.getParameter("leagueID"));
        String playerName = request.getParameter("playerName");

        League league = leagueDAO.getLeague(leagueId);
        request.setAttribute("league", league);

        SeasonWeek currentSeasonWeek = seasonDAO.getCurrentSeasonWeek();
        request.setAttribute("currentSeasonWeek", currentSeasonWeek);

        Team team = leaguePlayerDAO.getTeamForLeagueByPlayer(leagueId, playerName);
        request.setAttribute("team", team);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/application/leagues/team.jsp");
        requestDispatcher.forward(request, response);
    }
}
