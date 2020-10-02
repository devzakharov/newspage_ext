package com.zrv.newspage.dao;

import com.zrv.newspage.domain.User;
import com.zrv.newspage.service.CryptoService;
import com.zrv.newspage.service.DatabaseConnectionService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserDao implements Dao<User> {

    private static final DatabaseConnectionService dbcs = DatabaseConnectionService.getInstance();
    private static final CryptoService cryptoService = CryptoService.getInstance();
    private static UserDao instance;
    // TODO настроить адекватные события для логера

    List<User> users = new ArrayList<>();

    private UserDao() {

    }

    public static UserDao getInstance() {

        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    @Override
    public Optional<User> get(String id) {

        return Optional.ofNullable(users.get(Integer.parseInt(id)));
    }

    @Override
    public List<User> getAll() throws SQLException {

        ResultSet rs = dbcs.getConnection().prepareStatement("SELECT * FROM users").getResultSet();
        dbcs.closeConnection();

        return getUsersList(rs);
    }

    public Integer getUsersCount(User user) throws SQLException {

        int count = 0;

        String query = "SELECT count(*) FROM users WHERE login = ? OR email = ?";
        PreparedStatement preparedStatement = dbcs.getConnection().prepareStatement(query);
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());

        try {
            ResultSet rs = dbcs.getConnection().prepareStatement(query).executeQuery();
            if (rs != null && rs.next()) {
                count = rs.getInt(1);
            }
            dbcs.closeConnection();
            return count;
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
        }

        return count;
    }

    @Override
    public void save(User user) throws SQLException {

        String query = "INSERT INTO users (login, email, password) VALUES (?, ?, ?)";

        PreparedStatement preparedStatement = dbcs.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getEmail());
        preparedStatement.setString(3, cryptoService.getHashString(user.getPassword()));

        ResultSet rs = dbcs.getConnection().createStatement().getGeneratedKeys();
        dbcs.closeConnection();

        int id = -1;

        if (rs != null && rs.next()) {
            id = rs.getInt(1);
        }

        user.setId(id);
    }

    @Override
    public void update(User user, String[] params) {

        user.setLogin(Objects.requireNonNull(params[0], "Login cannot be null"));
        user.setEmail(Objects.requireNonNull(params[1], "Email cannot be null"));
        user.setPassword(Objects.requireNonNull(params[2], "Password cannot be null"));

        users.add(user);
    }

    @Override
    public void delete(User user) {

        users.remove(user);
    }

    private List<User> getUsersList(ResultSet rs) throws SQLException {

        while (rs.next()) {
            int id = rs.getInt("id");
            String login = rs.getString("login");
            String email = rs.getString("email");
            String password = rs.getString("password");
            int role_id = rs.getInt("role_id");

            User user = new User(login, email, password);

            users.add(user);
        }

        return users;
    }
}
