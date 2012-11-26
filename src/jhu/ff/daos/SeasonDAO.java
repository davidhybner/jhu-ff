package jhu.ff.daos;

import jhu.ff.helpers.ConnectionPool;
import jhu.ff.models.SeasonWeek;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SeasonDAO {
    private static SeasonDAO instance = null;
    private ConnectionPool connectionPool;

    private SeasonDAO() {
        connectionPool = ConnectionPool.getInstance();
    }

    public synchronized static SeasonDAO getInstance() {
        if(instance == null) {
            instance = new SeasonDAO();
        }

        return instance;
    }

    public SeasonWeek getCurrentSeasonWeek() {
        SeasonWeek result = null;

        Connection database = connectionPool.getConnection();
        try {
            PreparedStatement preparedStatement = database.prepareStatement("SELECT * FROM season ORDER BY year, week DESC");
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                int year = resultSet.getInt("year");
                int week = resultSet.getInt("week");
                result = new SeasonWeek(year, week);
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
