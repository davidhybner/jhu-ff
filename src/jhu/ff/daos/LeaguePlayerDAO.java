package jhu.ff.daos;

import jhu.ff.helpers.ConnectionPool;
import jhu.ff.helpers.Constants;

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
            preparedStatement = database.prepareStatement("insert into league_players values(?, ?)");
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
}
