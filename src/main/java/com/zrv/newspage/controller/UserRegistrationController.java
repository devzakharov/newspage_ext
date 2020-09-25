package com.zrv.newspage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zrv.newspage.domain.User;
import com.zrv.newspage.exception.DuplicateUserException;
import com.zrv.newspage.exception.WrongUserDataException;
import com.zrv.newspage.service.UserServiceImpl;
import com.zrv.newspage.util.ServletUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.*;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class UserRegistrationController extends HttpServlet {

    // TODO настроить адекватные события для логера

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        handleRequest(req, resp);
    }

    public void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        ServletUtils.setAccessControlHeaders(resp);
        PrintWriter output = resp.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        StringBuilder stringBuffer = new StringBuilder();
        String line;

        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, String> map = objectMapper.readValue(stringBuffer.toString(), Map.class);

        User user = new User(map.get("login"), map.get("email"), map.get("password"));

        String json;

        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);

        try {
            UserServiceImpl userService = new UserServiceImpl(user);
            if (constraintViolations.size() > 0) {
                throw new WrongUserDataException(
                        constraintViolations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList())
                );
            }
            if (userService.checkUserExist()) {
                throw new DuplicateUserException("Такой пользователь уже существует!");
            }
            userService.addUser();
            resp.setStatus(HttpServletResponse.SC_OK);
            json = "{\"status\":\"Успех!\"}";
        } catch (WrongUserDataException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            json = objectMapper.writeValueAsString(e.getErrorsList());
        } catch (DuplicateUserException | SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            json = objectMapper.writeValueAsString(e.getMessage());
        }
        output.write(json);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        ServletUtils.setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
