package jhu.ff.daos;

import jhu.ff.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static UserDAO instance;
    private Connection database;

    private UserDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            database = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/jhu_ff", "root", "");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }

        return instance;
    }

    public List<User> getUsers() {
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
        }

        return results;
    }

    public User getUser(String username) {
        User result = null;

        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = database.prepareStatement("SELECT * FROM users WHERE username = ?");
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
        }

        return result;
    }

    public boolean createUser(User user, String password) {
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
        }

        return result;
    }

    public boolean deleteUser(String username) {
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
        }

        return result;
    }

    public static void main(String[] args) {
        UserDAO userDAO = UserDAO.getInstance();

        List<User> users = userDAO.getUsers();
        System.out.println(users);

        User user = userDAO.getUser("chris");
        System.out.println(user);

        User userToInsert = new User("BartMan");
        userToInsert.addRole("player");
        System.out.println(userDAO.createUser(userToInsert, "password"));

        System.out.println(userDAO.deleteUser(userToInsert.getUsername()));
    }
}
