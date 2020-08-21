package com.zrv.newspage.dao;

import com.zrv.newspage.domain.User;
import com.zrv.newspage.service.CryptoService;
import com.zrv.newspage.service.DatabaseQueryService;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserDao implements Dao<User> {

    private final List<User> users = new ArrayList<>();
    DatabaseQueryService db = new DatabaseQueryService();

    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(users.get((int) id));
    }

    public UserDao() {

    }

    @Override
    public List<User> getAll() throws SQLException {
        ResultSet rs = db.getResultSet("SELECT * FROM users");
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

    // передаем данные (login, email, password) для записи юзера в базу, затем пишем id в объект user
    @Override
    public void save(User user) throws SQLException {

        String query = "";

        try {
            query = String.format(
                    "INSERT INTO users (login, email, password) VALUES ('%s', '%s', '%s')",
                    user.getLogin(), user.getEmail(), CryptoService.getInstance().getHashString(user.getPassword())
            );
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }

        db.getStatement().executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
        ResultSet rs = db.getStatement().getGeneratedKeys();

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
        // user.setRole(Objects.requireNonNull(params[3]));

        users.add(user);
    }

    @Override
    public void delete(User user) {
        users.remove(user);
    }
}
