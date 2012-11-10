package jhu.ff.daos;

import jhu.ff.helpers.ConnectionPool;
import jhu.ff.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static UserDAO instance;
    private ConnectionPool connectionPool;

    private UserDAO() {
        connectionPool = ConnectionPool.getInstance();
    }

    public static synchronized UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }

        return instance;
    }

    public List<User> getUsers() {
        Connection database = connectionPool.getConnection();

        List<User> results = new ArrayList<User>();
        try {
            PreparedStatement preparedStatement = database.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                results.add(new User(resultSet.getString("username")));
            }

            preparedStatement.close();
            resultSet.close();

            for(User user : results) {
                preparedStatement = database.prepareStatement("SELECT * FROM user_roles WHERE username = ?");
                preparedStatement.setString(1, user.getUsername());
                resultSet = preparedStatement.executeQuery();

                while(resultSet.next()) {
                    user.addRole(resultSet.getString("role"));
                }

                preparedStatement.close();
                resultSet.close();
           }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(database);
        }

        return results;
    }

    public User getUser(String username) {
        Connection database = connectionPool.getConnection();

        User result = null;
        try {
            PreparedStatement preparedStatement = database.prepareStatement("SELECT * FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                result = new User(resultSet.getString("username"));
            }

            resultSet.close();
            preparedStatement.close();

            if(result != null) {
                preparedStatement = database.prepareStatement("SELECT * FROM user_roles WHERE username = ?");
                preparedStatement.setString(1, username);
                resultSet = preparedStatement.executeQuery();

                while(resultSet.next()) {
                    result.addRole(resultSet.getString("role"));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(database);
        }

        return result;
    }

    public boolean createUser(User user, String password) {
        Connection database = connectionPool.getConnection();

        boolean result = false;
        try {
            PreparedStatement preparedStatement = database.prepareStatement("INSERT INTO users VALUES (?, ?)");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, password);
            int rowsInserted = preparedStatement.executeUpdate();

            preparedStatement.close();

            if(rowsInserted > 0) {
                result = true;

                for(String role : user.getRoles()) {
                    preparedStatement = database.prepareStatement("INSERT INTO user_roles VALUES (?, ?)");
                    preparedStatement.setString(1, user.getUsername());
                    preparedStatement.setString(2, role);
                    preparedStatement.executeUpdate();
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

    public boolean deleteUser(String username) {
        Connection database = connectionPool.getConnection();

        boolean result = false;
        try {
            PreparedStatement preparedStatement = database.prepareStatement("DELETE FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            int rowsDeleted = preparedStatement.executeUpdate();

            preparedStatement.close();
            if(rowsDeleted > 0) {
                result = true;

                preparedStatement = database.prepareStatement("DELETE FROM user_roles WHERE username = ?");
                preparedStatement.setString(1, username);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.closeConnection(database);
        }

        return result;
    }
}
