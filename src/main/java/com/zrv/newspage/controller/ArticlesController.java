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
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ArticlesController extends HttpServlet {

    // TODO настроить адекватные события для логера

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            handleRequest(req, resp);
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            handleRequest(req, resp);
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, ParseException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        setAccessControlHeaders(resp);
        PrintWriter output = resp.getWriter();
        ArticlesServiceImpl articlesService = new ArticlesServiceImpl();

        ObjectMapper mapper = new ObjectMapper();

        List<Article> requestedList = new LinkedList<>();

        System.out.println(req.getParameter("search"));

        if (req.getParameter("search") != null) {
            requestedList = articlesService.getSearchResults(req.getParameter("search"));
        } else {
            requestedList = articlesService.getArticlesList(
                    Integer.parseInt(req.getParameter("limit")),
                    Integer.parseInt(req.getParameter("offset")),
                    req.getParameter("tags"),
                    req.getParameter("fromDate"),
                    req.getParameter("toDate")
            );
        }

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
