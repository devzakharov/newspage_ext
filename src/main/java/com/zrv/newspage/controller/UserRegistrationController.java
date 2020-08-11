package com.zrv.newspage.controller;

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

        PrintWriter output = resp.getWriter();
        //resp.setContentType("text/html;charset=utf-8");
        //resp.setStatus(HttpServletResponse.SC_OK);

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        User user = new User(login, email, password);


        Set<ConstraintViolation<User>> constraintViolations = validator.validate( user );

        try {
            if (constraintViolations.size() > 0) throw new WrongUserDataException();
        } catch (WrongUserDataException e) {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            output.println("Shit nigga");
        }


//        if (login == null || login.isEmpty()) {
//            output.println("Login is null!");
//        } else {
//            output.println(login);
//        }
//
//        if (password == null || password.isEmpty()) {
//            output.println("Password is null!");
//        } else {
//            output.println(password);
//        }
    }
}
