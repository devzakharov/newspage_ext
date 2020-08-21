package com.zrv.newspage.service;
import com.zrv.newspage.dao.UserDao;
import com.zrv.newspage.domain.User;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    private User user;
    UserDao userDao;

    @Override
    public void addUser() {
        //System.out.println(this.user.toString());

        userDao = new UserDao();

        try {
            userDao.save(user);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void deleteUser() {

    }

    @Override
    public void setUserRole() {

    }

    @Override
    public void checkUserExist() {

    }

    public UserServiceImpl(User user) {
        this.user = user;
    }
}
