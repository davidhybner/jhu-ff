package jhu.ff.daos;

import jhu.ff.helpers.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
            PreparedStatement preparedStatement = database.prepareStatement("insert into league_players values(?, ?)");
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
