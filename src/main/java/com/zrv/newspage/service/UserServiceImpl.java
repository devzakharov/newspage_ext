package com.zrv.newspage.service;
import com.zrv.newspage.dao.UserDao;
import com.zrv.newspage.domain.User;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    private static UserServiceImpl instance;
    private User user;
    private final UserDao userDao = UserDao.getInstance();

    private UserServiceImpl( ) {

    }

    public static UserServiceImpl getInstance() {

        if (instance == null) {
            instance = new UserServiceImpl();
        }
        return instance;
    }

    @Override
    public void addUser() {

        try {
            userDao.save(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser() {

    }

    @Override
    public void setUserRole() {

    }

    @Override
    public Boolean checkUserExist() throws SQLException {

        return userDao.getUsersCount(user) > 0;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
