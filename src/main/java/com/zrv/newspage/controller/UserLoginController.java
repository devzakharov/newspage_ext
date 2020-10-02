package com.zrv.newspage.controller;

import com.zrv.newspage.util.ServletUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class UserLoginController extends HttpServlet {

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

        ServletUtils.setUsualHeaders(resp);
        ServletUtils.setAccessControlHeaders(resp);
        PrintWriter output = resp.getWriter();

//        StringBuffer stringBuffer = new StringBuffer();
//        String line = "";
        //BufferedReader reader = req.getReader();

        // TODO реализовать UserLoginService

//        try {
//            while ((line = reader.readLine()) != null)
//                stringBuffer.append(line);
//        } catch (Exception e) { /*report an error*/ }

//        System.out.println(reader);
//        System.out.println(line);
//        output.println(line);
    }

    //for Preflight
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {

        ServletUtils.setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

}
