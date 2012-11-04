package jhu.ff.daos;

import jhu.ff.models.League;
import jhu.ff.models.Roster;
import jhu.ff.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeagueDAO {
    private Connection database;
    private static LeagueDAO instance;

    private LeagueDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            database = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/jhu_ff", "root", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized LeagueDAO getInstance() {
        if(instance == null) {
            instance = new LeagueDAO();
        }

        return instance;
    }

    public List<League> getLeagues() {
        List<League> results = new ArrayList<League>();
        try {
            PreparedStatement preparedStatement = database.prepareStatement("SELECT * FROM leagues");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                League league = new League(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("owner"));
                results.add(league);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    public League getLeague(int leagueId) {
        League result = null;

        try {
            PreparedStatement preparedStatement = database.prepareStatement("SELECT * FROM leagues WHERE id = ?");
            preparedStatement.setInt(1, leagueId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                result = new League(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("owner"));
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
        }

        return result;
    }

    public int createLeague(String name, String owner) {
        int result = -1;
        try {
            PreparedStatement preparedStatement = database.prepareStatement("INSERT INTO leagues(name, owner) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, owner);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                result = generatedKeys.getInt(1);
            }

            generatedKeys.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public boolean deleteLeague(int leagueId) {
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
        }

        return result;
    }

    public static void main(String[] args) {
        LeagueDAO leagueDAO = LeagueDAO.getInstance();
        System.out.println(leagueDAO.getLeagues());
        System.out.println(leagueDAO.getLeague(1));
        int leagueId = leagueDAO.createLeague("test league", "chris");
        System.out.println(leagueId);
        System.out.println(leagueDAO.deleteLeague(leagueId));
    }
}
