package com.omega.sql;

import java.math.RoundingMode;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLHandler {
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement pstmt;
    private static String DBPath = "jdbc:sqlite:telemedicine.db";

    private static SQLHandler instance;

    private SQLHandler() {}

    public static SQLHandler getInstance() {
        if (instance == null){
            connect();
            instance = new SQLHandler();
        }
        return instance;
    }

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
     * Closing DB connection
     */
    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
