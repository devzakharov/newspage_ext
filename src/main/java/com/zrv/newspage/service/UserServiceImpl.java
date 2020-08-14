package com.zrv.newspage.service;

import com.zrv.newspage.domain.User;

public class UserServiceImpl implements UserService {
    private User user;

    @Override
    public void addUser() {
        System.out.println(this.user.toString());
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
