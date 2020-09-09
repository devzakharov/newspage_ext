package com.zrv.newspage.service;

import java.sql.*;

public class DatabaseConnectionService {

    private static final String url = "jdbc:postgresql://localhost:5432/newspage?useUnicode=true&serverTimezone=UTC";
    private static final String user = "postgres";
    private static final String password = "root";

    private static Connection connection;

    public DatabaseConnectionService() {

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection () {

        return connection;
    }

}
