package com.zrv.newspage.dao;

import com.zrv.newspage.domain.User;
import com.zrv.newspage.service.CryptoService;
import com.zrv.newspage.service.DatabaseConnectionService;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserDao implements Dao<User> {

    // TODO настроить адекватные события для логера

    private final List<User> users = new ArrayList<>();

    @Override
    public Optional<User> get(String id) {
        return Optional.ofNullable(users.get(Integer.parseInt(id)));
    }

    public UserDao() {

    }

    @Override
    public List<User> getAll() throws SQLException {
        DatabaseConnectionService db = new DatabaseConnectionService();
        ResultSet rs = db.getConnection().prepareStatement("SELECT * FROM users").getResultSet();
        db.closeConnection();
        // TODO вынести формирование листа в метод
        while (rs.next()) {
            int id = rs.getInt("id");
            String login = rs.getString("login");
            String email = rs.getString("email");
            String password = rs.getString("password");
            int role_id = rs.getInt("role_id");

            //Assuming you have a user object
            User user = new User(login, email, password);

            users.add(user);
        }
        return users;
    }

    // TODO переименовать метод
    public Integer getUserByLogin(User user) throws SQLException {

        String query = "";
        int count = 0;
        DatabaseConnectionService db = new DatabaseConnectionService();


        query = String.format(
                "SELECT count(*) FROM users WHERE login = '%s' OR email = '%s'",
                user.getLogin(),
                user.getEmail()
        );

        try {

            ResultSet rs = db.getConnection().createStatement().executeQuery(query);
            db.closeConnection();

            if (rs != null && rs.next()) {
                count = rs.getInt(1);
            }

            System.out.println(count);

            return count;
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(e);
        }

        return count;
    }

    // передаем данные (login, email, password) для записи юзера в базу, затем пишем id в объект user
    @Override
    public void save(User user) throws SQLException {

        String query = "";
        DatabaseConnectionService db = new DatabaseConnectionService();

        try {
            query = String.format(
                    "INSERT INTO users (login, email, password) VALUES ('%s', '%s', '%s')",
                    user.getLogin(), user.getEmail(), CryptoService.getInstance().getHashString(user.getPassword())
            );
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }

        try {
            db.getConnection().createStatement().executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(e);
        }

        ResultSet rs = db.getConnection().createStatement().getGeneratedKeys();
        db.closeConnection();


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
}
