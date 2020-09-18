package com.zrv.newspage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zrv.newspage.domain.User;
import com.zrv.newspage.exception.DuplicateUserException;
import com.zrv.newspage.exception.WrongUserDataException;
import com.zrv.newspage.service.UserServiceImpl;

import javax.servlet.ServletException;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        handleRequest(req, resp);
    }

    // TODO разнести логику по методам
    public void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        setAccessControlHeaders(resp);
        PrintWriter output = resp.getWriter();

        StringBuilder stringBuffer = new StringBuilder();
        String line = "";

        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                stringBuffer.append(line);
        } catch (Exception e) { /*report an error*/ }

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> map = objectMapper.readValue(stringBuffer.toString(), Map.class);

        String login = map.get("login");
        String password = map.get("password");
        String email = map.get("email");

        User user = new User(login, email, password);

        String json;

        Set<ConstraintViolation<User>> constraintViolations = validator.validate( user );

        try {

            UserServiceImpl userService = new UserServiceImpl(user);

            if (constraintViolations.size() > 0) throw new WrongUserDataException(constraintViolations.stream().map(item -> item.getMessage()).collect(Collectors.toList()));

            if (userService.checkUserExist()) throw new DuplicateUserException("Такой пользователь уже существует!");

            userService.addUser();

            resp.setStatus(HttpServletResponse.SC_OK);

            json = "{\"status\":\"Успех!\"}";

        } catch (WrongUserDataException e) {

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            json = objectMapper.writeValueAsString(e.getErrorsList());
        } catch (DuplicateUserException e) {

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            json = objectMapper.writeValueAsString(e.getMessage());
        } catch (SQLException e) {

            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            json = objectMapper.writeValueAsString(e.getMessage());
        }

        output.write(json);
    }

    //for Preflight
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        resp.setHeader("Access-Control-Max-Age", "1000");
        resp.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type, origin, authorization, accept, x-access-token");
    }
}
