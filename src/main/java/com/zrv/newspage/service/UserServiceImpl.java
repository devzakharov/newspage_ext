package com.zrv.newspage.service;
import com.zrv.newspage.dao.UserDao;
import com.zrv.newspage.domain.User;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    private final User user;
    private UserDao userDao = new UserDao();

    @Override
    public void addUser() {

        userDao = new UserDao();

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

    public UserServiceImpl(User user) {

        this.user = user;
    }
}
