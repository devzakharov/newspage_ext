package com.zrv.newspage.service;

import java.sql.*;

public class DatabaseConnectionService {

    private static DatabaseConnectionService instance;

    private static final String url = "jdbc:postgresql://localhost:5432/test2?useUnicode=true&serverTimezone=UTC";
    private static final String user = "postgres";
    private static final String password = "root";

    private static Connection connection;

    private DatabaseConnectionService() {

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseConnectionService getInstance() {

        if (instance == null) {
            instance = new DatabaseConnectionService();
        }
        return instance;
    }

    public Connection getConnection () {

        return connection;
    }

    public void closeConnection () {

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
