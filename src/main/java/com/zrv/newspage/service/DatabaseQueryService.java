package com.zrv.newspage.service;

import java.sql.*;

public class DatabaseQueryService {

    private static final String url = "jdbc:mysql://localhost:3306/newspage?useUnicode=true&serverTimezone=UTC";
    private static final String user = "root";
    private static final String password = "";

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public DatabaseQueryService() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            setStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getResultSet(String query) throws SQLException {
        resultSet = statement.executeQuery(query);
        return resultSet;
    }

    public Statement getStatement() {
        return statement;
    }

    private void setStatement() throws SQLException {
        statement = connection.createStatement();
    }

}
