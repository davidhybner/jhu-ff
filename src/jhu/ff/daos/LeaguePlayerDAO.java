package jhu.ff.daos;

import jhu.ff.helpers.ConnectionPool;
import jhu.ff.helpers.Constants;
import jhu.ff.models.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LeaguePlayerDAO {
    private static LeaguePlayerDAO instance = null;
    private ConnectionPool connectionPool;

    private LeaguePlayerDAO() {
        connectionPool = ConnectionPool.getInstance();
    }

    public synchronized static LeaguePlayerDAO getInstance() {
        if(instance == null) {
            instance = new LeaguePlayerDAO();
        }

        return instance;
    }

    public boolean addPlayerToLeague(int leagueID, String username) {
        boolean result = false;
        Connection database = connectionPool.getConnection();

        try {
            // check to see if we have space for another player
            PreparedStatement preparedStatement = database.prepareStatement("SELECT COUNT(*) FROM league_players WHERE league_id = ?");
            preparedStatement.setInt(1, leagueID);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.first();
            if(resultSet.getInt(1) > Constants.MAX_PLAYERS_IN_LEAGUE) {
                resultSet.close();
                preparedStatement.close();
                return false;
            }

            resultSet.close();
            preparedStatement.close();

            // check to see if the player is already a member of that league
            preparedStatement = database.prepareStatement("SELECT * FROM league_players WHERE league_id = ? AND username = ?");
            preparedStatement.setInt(1, leagueID);
            preparedStatement.setString(2, username);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.first()) {
                resultSet.close();
                preparedStatement.close();
                return false;
            }

            resultSet.close();
            preparedStatement.close();

            // we know we have space and the user isn't already in the league, so add the player
            preparedStatement = database.prepareStatement("INSERT INTO league_players VALUES(?, ?, 0, 0, 0)");
            preparedStatement.setInt(1, leagueID);
            preparedStatement.setString(2, username);
            int rowsAffected = preparedStatement.executeUpdate();

            if(rowsAffected > 0) {
                result = true;
            }

            preparedStatement.close();
            connectionPool.closeConnection(database);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

        public boolean draftTeam(int leagueId, int teamId, String playerName, boolean offense) {
        Connection database = connectionPool.getConnection();

        boolean result = false;
        String team = offense ? "offense_team" : "defense_team";
        try {
            PreparedStatement preparedStatement = database.prepareStatement("SELECT * FROM league_players WHERE league_id = ? AND " + team + " = ?");
            preparedStatement.setInt(1, leagueId);
            preparedStatement.setInt(2, teamId);
            ResultSet resultSet = preparedStatement.executeQuery();

            // if no records are found, it is valid to add the team for this player
            if(!resultSet.first()) {
                resultSet.close();
                preparedStatement.close();

                preparedStatement = database.prepareStatement("UPDATE league_players WHERE league_id = ? AND username = ? SET " + team + " = ?");
                preparedStatement.setInt(1, leagueId);
                preparedStatement.setString(2, playerName);
                preparedStatement.setInt(3, teamId);
                int rowsUpdated = preparedStatement.executeUpdate();

                if(rowsUpdated > 0) {
                    result = true;

                    resultSet.close();
                    preparedStatement.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(database);
        }

        return result;
    }

    public Team getTeamForLeagueByPlayer(int leagueID, String playerName) {
        Team result = null;

        Connection database = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = database.prepareStatement("SELECT * FROM league_players WHERE league_id = ? AND username = ?");
            preparedStatement.setInt(1, leagueID);
            preparedStatement.setString(2, playerName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int offenseTeam = resultSet.getInt("offense_team");
                int defenseTeam = resultSet.getInt("defense_team");
                int score = resultSet.getInt("score");

                result = new Team(offenseTeam, defenseTeam, score);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(database);
        }

        return result;
    }
}
