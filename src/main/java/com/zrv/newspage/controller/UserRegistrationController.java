package com.zrv.newspage.controller;

import com.google.gson.Gson;
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

        PrintWriter output = resp.getWriter();

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        User user = new User(login, email, password);

        String json;

        Set<ConstraintViolation<User>> constraintViolations = validator.validate( user );

        try {

            if (constraintViolations.size() > 0) throw new WrongUserDataException(constraintViolations.stream().map(item -> item.getMessage()).collect(Collectors.toList()));

            resp.setStatus(HttpServletResponse.SC_OK);

            json = "{status:'success'}";

        } catch (WrongUserDataException e) {

            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            json = new Gson().toJson(e.getErrorsList());
        }

        output.write(json);
    }
}
