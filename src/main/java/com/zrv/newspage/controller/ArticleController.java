package com.zrv.newspage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zrv.newspage.domain.Article;
import com.zrv.newspage.service.ArticleService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class ArticleController extends HttpServlet {

    // TODO настроить адекватные события для логера

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

        ArticleService articleService = new ArticleService();

        Article article = articleService.getArticle(req.getParameter("id"));

        ObjectMapper mapper = new ObjectMapper();

        output.write(mapper.writeValueAsString(article));

    }

    // TODO создать объект с дуОпшнс и сетХедерс ???
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
