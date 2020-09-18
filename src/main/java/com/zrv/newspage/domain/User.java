package com.zrv.newspage.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class User {

    private int id;

    @NotNull(message = "Логин не может быть пустым!")
    @Size(min = 3, max = 20, message = "Логин должен содержать от 3 до 20 символов!")
    private String login;

    @NotNull(message = "Емейл не может быть пустым!")
    @Email(message = "Введите емейл в формате example@example.ru!")
    private String email;

    private Role role = Role.USER;

    @NotNull(message = "Пароль не может быть пустым!")
    @Size(min = 6, max = 20, message = "Пароль должен содержать от 6 до 20 символов!")
    private String password;

    public User(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public int getId() { return id;}

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                '}';
    }
}