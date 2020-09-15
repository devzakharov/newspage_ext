package com.zrv.newspage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zrv.newspage.domain.Article;
import com.zrv.newspage.service.ArticlesServiceImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ArticlesController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            handleRequest(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            handleRequest(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        setAccessControlHeaders(resp);
        PrintWriter output = resp.getWriter();
        ArticlesServiceImpl articlesService = new ArticlesServiceImpl();

        // String jsonString = req.getReader().readLine();
        ObjectMapper mapper = new ObjectMapper();

        HashMap<String, Integer> jsonMap = null;

        List<Article> requestedList = new LinkedList<>();

//        if (jsonString != "{}") {
//            try {
//
//                jsonMap = mapper.readValue(jsonString, HashMap.class);
//
//                System.out.println(jsonMap);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        assert jsonMap != null;
//
//        // here is list of requested articles
//       requestedList =  articlesService.getArticlesList(jsonMap.get("limit"), jsonMap.get("offset"));

//        } else {

           requestedList = articlesService.getArticlesList(
                   Integer.parseInt(req.getParameter("limit")),
                   Integer.parseInt(req.getParameter("offset")),
                   req.getParameter("tags")
           );
//        }



        output.write(mapper.writeValueAsString(requestedList));
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
