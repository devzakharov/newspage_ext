package com.zrv.newspage.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class UserLoginController extends HttpServlet {

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
        setAccessControlHeaders(resp);
        PrintWriter output = resp.getWriter();

        StringBuffer stringBuffer = new StringBuffer();
        String line;

        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                stringBuffer.append(line);
        } catch (Exception e) { /*report an error*/ }

        JsonObject jsonObject = new JsonParser().parse(stringBuffer.toString()).getAsJsonObject();

        String login = jsonObject.get("login").getAsString();
        String password = jsonObject.get("password").getAsString();
        String email = jsonObject.get("email").getAsString();
    }

    //for Preflight
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
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
