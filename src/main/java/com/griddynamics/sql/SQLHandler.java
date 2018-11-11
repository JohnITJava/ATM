package com.griddynamics.sql;

import com.griddynamics.entity.Admin;
import com.griddynamics.entity.Human;
import com.griddynamics.entity.Operation;
import com.griddynamics.entity.User;

import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SQLHandler class provides connection and
 * describes interaction to DB with different objects
 */

public class SQLHandler {
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement pstmt;
    private static String DBPath = "jdbc:sqlite:atm.db";
    private static String testDBPath = "jdbc:sqlite:atm_test.db";


    /**
     * Create connection to origin DB
     */
    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(DBPath);
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Create connection for testing DB
     */
    public static void connectToTestDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:atm_test.db");
            connection.setAutoCommit(false);
            connection.setSavepoint();
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param username will getting from GUI.
     * @param password will getting from GUI.
     * @return true if registration is success. It will be reached if username is unique.
     */
    public static boolean tryToRegister(String username, String password) {
        try {
            stmt.executeUpdate(String.format("INSERT INTO humans (role, username, password, balance) " +
                    "VALUES ('%s', '%s', '%s', '%d');", "User", username, password, 0));
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param username will getting from GUI.
     * @param password will getting from GUI.
     * @return true if log in is success. It will be reached if DB has it combination of name and pass.
     */
    public static boolean tryToLogin(String username, String password) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM humans WHERE username = '" + username +
                    "' AND password = '" + password + "';");
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @return balance from DB by name.
     */
    public static double getUserBalance(String name) {
        double balance = 0;
        try {
            pstmt = connection.prepareStatement("SELECT balance FROM humans WHERE username = ?;");
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            balance = Double.parseDouble((rs.getBigDecimal("balance")).setScale(2, RoundingMode.DOWN)
                    .toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    /**
     * @return Operation object that contains last three user's operations by name
     */
    public static Operation getLastUserOper(String name) {
        Operation operations = new Operation();
        try {
            pstmt = connection.prepareStatement("SELECT operation FROM operations LEFT JOIN humans ON id_hum = humans.id " +
                    "WHERE username = ? ORDER BY operations.id DESC LIMIT 3; ");
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                operations.addOperationToList(rs.getString("operation"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return operations;
    }

    /**
     * @param username
     * @param password
     * @return User or Admin object which inherited from Human class.
     * Type of object determined by column 'role'. Human returns to live in runtime session.
     */
    public static Human getHumanFromDB(String username, String password) {
        Human human;
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM humans WHERE username = '" + username +
                    "' AND password = '" + password + "';");
            if ("User".equals(rs.getString("role"))) {
                human = new User(username, password);
                ((User) human).setId(rs.getInt("id"));
            } else {
                human = new Admin(username, password);
                ((Admin) human).setId(rs.getInt("id"));

            }
            return human;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param username
     * @param money
     * Method update money in DB by user's name
     */
    public static void addMoney(String username, double money) {
        try {
            pstmt = connection.prepareStatement("UPDATE humans SET balance = humans.balance + ? WHERE username = ?");
            pstmt.setDouble(1, money);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param username
     * @param money
     * Method update money in DB by user's name
     */
    public static void substructMoney(String username, double money) {
        try {
            pstmt = connection.prepareStatement("UPDATE humans SET balance = humans.balance - ? WHERE username = ?");
            pstmt.setDouble(1, money);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param humanId
     * @param operation will be added in DB record by user's id
     */
    public static void insertOperation(int humanId, String operation) {
        try {
            pstmt = connection.prepareStatement("INSERT INTO operations(operation, id_hum) VALUES (?, ?);");
            pstmt.setString(1, operation);
            pstmt.setInt(2, humanId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return all user's names in list where role of human is 'User'
     */
    public static List<String> getAllUsersNamesDB() {
        List<String> allUsers = new ArrayList<>();
        try {
            ResultSet rs = stmt.executeQuery("SELECT username FROM humans WHERE role = 'User'");
            while (rs.next()) {
                allUsers.add(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }


    /**
     * Closing DB connection
     */
    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closing testing DB connection
     */
    public static void disconnectFromTestDB() {
        try {
            stmt.close();
            connection.rollback();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
