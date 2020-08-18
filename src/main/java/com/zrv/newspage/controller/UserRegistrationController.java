package com.zrv.newspage.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zrv.newspage.domain.User;
import com.zrv.newspage.exception.WrongUserDataException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.stream.Collectors;

public class UserRegistrationController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        handleRequest(req, resp);
    }

    public void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        setAccessControlHeaders(resp);
        PrintWriter output = resp.getWriter();

        StringBuffer stringBuffer = new StringBuffer();
        String line;

        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                stringBuffer.append(line);
        } catch (Exception e) { /*report an error*/ }

        // TODO: Switch JSON library to Jackson
        JsonObject jsonObject = new JsonParser().parse(stringBuffer.toString()).getAsJsonObject();

        String login = jsonObject.get("login").getAsString();
        String password = jsonObject.get("password").getAsString();
        String email = jsonObject.get("email").getAsString();

        User user = new User(login, email, password);

        System.out.println(user.toString());

        String json;

        Set<ConstraintViolation<User>> constraintViolations = validator.validate( user );

        try {

            if (constraintViolations.size() > 0) throw new WrongUserDataException(constraintViolations.stream().map(item -> item.getMessage()).collect(Collectors.toList()));

            resp.setStatus(HttpServletResponse.SC_OK);

            json = "{\"status\":\"success\"}";

        } catch (WrongUserDataException e) {

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            // TODO: Switch JSON library to Jackson
            json = new Gson().toJson(e.getErrorsList());
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
