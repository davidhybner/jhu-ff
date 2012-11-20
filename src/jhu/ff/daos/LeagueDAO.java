package jhu.ff.daos;

import jhu.ff.helpers.ConnectionPool;
import jhu.ff.models.League;
import jhu.ff.models.Roster;
import jhu.ff.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LeagueDAO {
    private ConnectionPool connectionPool;
    private static LeagueDAO instance;

    private LeagueDAO() {
        connectionPool = ConnectionPool.getInstance();
    }

    public static synchronized LeagueDAO getInstance() {
        if(instance == null) {
            instance = new LeagueDAO();
        }

        return instance;
    }

    public List<League> getLeagues() {
        Connection database = connectionPool.getConnection();

        List<League> results = new ArrayList<League>();
        try {
            PreparedStatement preparedStatement = database.prepareStatement("SELECT * FROM leagues");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                League league = new League(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("owner"), resultSet.getString("public_id"));
                results.add(league);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(database);
        }

        return results;
    }

    public League getLeague(int leagueId) {
        Connection database = connectionPool.getConnection();

        League result = null;
        try {
            PreparedStatement preparedStatement = database.prepareStatement("SELECT * FROM leagues WHERE id = ?");
            preparedStatement.setInt(1, leagueId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                result = new League(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("owner"), resultSet.getString("public_id"));
            }

            resultSet.close();
            preparedStatement.close();

            if(result != null) {
                preparedStatement = database.prepareStatement("SELECT * FROM league_rosters WHERE league_id = ?");
                preparedStatement.setInt(1, leagueId);
                resultSet = preparedStatement.executeQuery();

                while(resultSet.next()) {
                    User user = UserDAO.getInstance().getUser(resultSet.getString("player"));
                    Roster roster = new Roster(resultSet.getString("offenseTeam"), resultSet.getString("defenseTeam"));
                    result.addPlayerRoster(user, roster);
                }

                resultSet.close();
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(database);
        }

        return result;
    }

    public int createLeague(String name, String owner) {
        Connection database = connectionPool.getConnection();

        int result = -1;
        try {
            PreparedStatement preparedStatement = database.prepareStatement("INSERT INTO leagues(name, owner, public_id) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, owner);
            preparedStatement.setString(3, UUID.randomUUID().toString());
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                result = generatedKeys.getInt(1);
            }

            generatedKeys.close();
            preparedStatement.close();

            // always add the owner of the league as a player
            if(result > -1) {
                LeaguePlayerDAO.getInstance().addPlayerToLeague(result, owner);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(database);
        }

        return result;
    }

    public boolean deleteLeague(int leagueId) {
        Connection database = connectionPool.getConnection();

        boolean result = false;
        try {
            PreparedStatement preparedStatement = database.prepareStatement("DELETE FROM leagues WHERE id = ?");
            preparedStatement.setInt(1, leagueId);
            int rowsDeleted = preparedStatement.executeUpdate();

            if(rowsDeleted > 0) {
                result = true;
            }

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(database);
        }

        return result;
    }

    public League findLeagueByPublicId(String leaguePublicId) {
        Connection database = connectionPool.getConnection();

        League result = null;
        try {
            PreparedStatement preparedStatement = database.prepareStatement("SELECT * FROM leagues WHERE public_id = ?");
            preparedStatement.setString(1, leaguePublicId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                result = new League(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("owner"), resultSet.getString("public_id"));
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }  finally {
            connectionPool.closeConnection(database);
        }

        return result;
    }

    public List<League> findLeaguesByPlayer(String playerName) {
        Connection database = connectionPool.getConnection();

        List<League> results = new ArrayList<League>();
        try {
            PreparedStatement preparedStatement = database.prepareStatement("SELECT * FROM league_players as lp JOIN leagues as l WHERE lp.league_id = l.id AND lp.username = ?");
            preparedStatement.setString(1, playerName);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                League league = new League(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("owner"), resultSet.getString("public_id"));
                results.add(league);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(database);
        }

        return results;
    }
}
