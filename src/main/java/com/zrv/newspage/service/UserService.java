package com.zrv.newspage.service;

import com.zrv.newspage.domain.User;

import java.sql.SQLException;

public interface UserService {

    void addUser();
    void deleteUser();
    void setUserRole();
    Boolean checkUserExist() throws SQLException;
}
